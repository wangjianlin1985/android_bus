package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.BusLineDAO;
import com.chengxusheji.domain.BusLine;
import com.chengxusheji.dao.BusStationDAO;
import com.chengxusheji.domain.BusStation;
import com.chengxusheji.dao.BusStationDAO;
import com.chengxusheji.domain.BusStation;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class BusLineAction extends BaseAction {

    /*界面层需要查询的属性: 线路名称*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 起点站*/
    private BusStation startStation;
    public void setStartStation(BusStation startStation) {
        this.startStation = startStation;
    }
    public BusStation getStartStation() {
        return this.startStation;
    }

    /*界面层需要查询的属性: 终到站*/
    private BusStation endStation;
    public void setEndStation(BusStation endStation) {
        this.endStation = endStation;
    }
    public BusStation getEndStation() {
        return this.endStation;
    }

    /*界面层需要查询的属性: 所属公司*/
    private String company;
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return this.company;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int lineId;
    public void setLineId(int lineId) {
        this.lineId = lineId;
    }
    public int getLineId() {
        return lineId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource BusStationDAO busStationDAO;
    @Resource BusLineDAO busLineDAO;

    /*待操作的BusLine对象*/
    private BusLine busLine;
    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }
    public BusLine getBusLine() {
        return this.busLine;
    }

    /*跳转到添加BusLine视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的BusStation信息*/
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        return "add_view";
    }

    /*添加BusLine信息*/
    @SuppressWarnings("deprecation")
    public String AddBusLine() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(busLine.getStartStation().getStationId());
            busLine.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(busLine.getEndStation().getStationId());
            busLine.setEndStation(endStation);
            busLineDAO.AddBusLine(busLine);
            ctx.put("message",  java.net.URLEncoder.encode("BusLine添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusLine添加失败!"));
            return "error";
        }
    }

    /*查询BusLine信息*/
    public String QueryBusLine() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(company == null) company = "";
        List<BusLine> busLineList = busLineDAO.QueryBusLineInfo(name, startStation, endStation, company, currentPage);
        /*计算总的页数和总的记录数*/
        busLineDAO.CalculateTotalPageAndRecordNumber(name, startStation, endStation, company);
        /*获取到总的页码数目*/
        totalPage = busLineDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = busLineDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("busLineList",  busLineList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("name", name);
        ctx.put("startStation", startStation);
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("endStation", endStation);
        ctx.put("company", company);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryBusLineOutputToExcel() { 
        if(name == null) name = "";
        if(company == null) company = "";
        List<BusLine> busLineList = busLineDAO.QueryBusLineInfo(name,startStation,endStation,company);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BusLine信息记录"; 
        String[] headers = { "线路名称","起点站","终到站","首班车时间","末班车时间","所属公司"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<busLineList.size();i++) {
        	BusLine busLine = busLineList.get(i); 
        	dataset.add(new String[]{busLine.getName(),busLine.getStartStation().getStationName(),
busLine.getEndStation().getStationName(),
busLine.getStartTime(),busLine.getEndTime(),busLine.getCompany()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"BusLine.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询BusLine信息*/
    public String FrontQueryBusLine() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(company == null) company = "";
        List<BusLine> busLineList = busLineDAO.QueryBusLineInfo(name, startStation, endStation, company, currentPage);
        /*计算总的页数和总的记录数*/
        busLineDAO.CalculateTotalPageAndRecordNumber(name, startStation, endStation, company);
        /*获取到总的页码数目*/
        totalPage = busLineDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = busLineDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("busLineList",  busLineList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("name", name);
        ctx.put("startStation", startStation);
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("endStation", endStation);
        ctx.put("company", company);
        return "front_query_view";
    }

    /*查询要修改的BusLine信息*/
    public String ModifyBusLineQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键lineId获取BusLine对象*/
        BusLine busLine = busLineDAO.GetBusLineByLineId(lineId);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("busLine",  busLine);
        return "modify_view";
    }

    /*查询要修改的BusLine信息*/
    public String FrontShowBusLineQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键lineId获取BusLine对象*/
        BusLine busLine = busLineDAO.GetBusLineByLineId(lineId);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("busLine",  busLine);
        return "front_show_view";
    }

    /*更新修改BusLine信息*/
    public String ModifyBusLine() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(busLine.getStartStation().getStationId());
            busLine.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(busLine.getEndStation().getStationId());
            busLine.setEndStation(endStation);
            busLineDAO.UpdateBusLine(busLine);
            ctx.put("message",  java.net.URLEncoder.encode("BusLine信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusLine信息更新失败!"));
            return "error";
       }
   }

    /*删除BusLine信息*/
    public String DeleteBusLine() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            busLineDAO.DeleteBusLine(lineId);
            ctx.put("message",  java.net.URLEncoder.encode("BusLine删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusLine删除失败!"));
            return "error";
        }
    }

}
