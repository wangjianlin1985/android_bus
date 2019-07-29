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
import com.chengxusheji.dao.BusStationDAO;
import com.chengxusheji.domain.BusStation;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class BusStationAction extends BaseAction {

    /*界面层需要查询的属性: 站点名称*/
    private String stationName;
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public String getStationName() {
        return this.stationName;
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

    private int stationId;
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
    public int getStationId() {
        return stationId;
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

    /*待操作的BusStation对象*/
    private BusStation busStation;
    public void setBusStation(BusStation busStation) {
        this.busStation = busStation;
    }
    public BusStation getBusStation() {
        return this.busStation;
    }

    /*跳转到添加BusStation视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加BusStation信息*/
    @SuppressWarnings("deprecation")
    public String AddBusStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            busStationDAO.AddBusStation(busStation);
            ctx.put("message",  java.net.URLEncoder.encode("BusStation添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusStation添加失败!"));
            return "error";
        }
    }

    /*查询BusStation信息*/
    public String QueryBusStation() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        List<BusStation> busStationList = busStationDAO.QueryBusStationInfo(stationName, currentPage);
        /*计算总的页数和总的记录数*/
        busStationDAO.CalculateTotalPageAndRecordNumber(stationName);
        /*获取到总的页码数目*/
        totalPage = busStationDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = busStationDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("busStationList",  busStationList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stationName", stationName);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryBusStationOutputToExcel() { 
        if(stationName == null) stationName = "";
        List<BusStation> busStationList = busStationDAO.QueryBusStationInfo(stationName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BusStation信息记录"; 
        String[] headers = { "记录编号","站点名称","经度","纬度"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<busStationList.size();i++) {
        	BusStation busStation = busStationList.get(i); 
        	dataset.add(new String[]{busStation.getStationId() + "",busStation.getStationName(),busStation.getLongitude() + "",busStation.getLatitude() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"BusStation.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询BusStation信息*/
    public String FrontQueryBusStation() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        List<BusStation> busStationList = busStationDAO.QueryBusStationInfo(stationName, currentPage);
        /*计算总的页数和总的记录数*/
        busStationDAO.CalculateTotalPageAndRecordNumber(stationName);
        /*获取到总的页码数目*/
        totalPage = busStationDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = busStationDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("busStationList",  busStationList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stationName", stationName);
        return "front_query_view";
    }

    /*查询要修改的BusStation信息*/
    public String ModifyBusStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stationId获取BusStation对象*/
        BusStation busStation = busStationDAO.GetBusStationByStationId(stationId);

        ctx.put("busStation",  busStation);
        return "modify_view";
    }

    /*查询要修改的BusStation信息*/
    public String FrontShowBusStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stationId获取BusStation对象*/
        BusStation busStation = busStationDAO.GetBusStationByStationId(stationId);

        ctx.put("busStation",  busStation);
        return "front_show_view";
    }

    /*更新修改BusStation信息*/
    public String ModifyBusStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            busStationDAO.UpdateBusStation(busStation);
            ctx.put("message",  java.net.URLEncoder.encode("BusStation信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusStation信息更新失败!"));
            return "error";
       }
   }

    /*删除BusStation信息*/
    public String DeleteBusStation() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            busStationDAO.DeleteBusStation(stationId);
            ctx.put("message",  java.net.URLEncoder.encode("BusStation删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusStation删除失败!"));
            return "error";
        }
    }

}
