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
import com.chengxusheji.dao.StationToStationDAO;
import com.chengxusheji.domain.StationToStation;
import com.chengxusheji.dao.BusStationDAO;
import com.chengxusheji.domain.BusStation;
import com.chengxusheji.dao.BusStationDAO;
import com.chengxusheji.domain.BusStation;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class StationToStationAction extends BaseAction {

    /*界面层需要查询的属性: 起始站*/
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

    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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
    @Resource StationToStationDAO stationToStationDAO;

    /*待操作的StationToStation对象*/
    private StationToStation stationToStation;
    public void setStationToStation(StationToStation stationToStation) {
        this.stationToStation = stationToStation;
    }
    public StationToStation getStationToStation() {
        return this.stationToStation;
    }

    /*跳转到添加StationToStation视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的BusStation信息*/
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        return "add_view";
    }

    /*添加StationToStation信息*/
    @SuppressWarnings("deprecation")
    public String AddStationToStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(stationToStation.getStartStation().getStationId());
            stationToStation.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(stationToStation.getEndStation().getStationId());
            stationToStation.setEndStation(endStation);
            stationToStationDAO.AddStationToStation(stationToStation);
            ctx.put("message",  java.net.URLEncoder.encode("StationToStation添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationToStation添加失败!"));
            return "error";
        }
    }

    /*查询StationToStation信息*/
    public String QueryStationToStation() {
        if(currentPage == 0) currentPage = 1;
        List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStationInfo(startStation, endStation, currentPage);
        /*计算总的页数和总的记录数*/
        stationToStationDAO.CalculateTotalPageAndRecordNumber(startStation, endStation);
        /*获取到总的页码数目*/
        totalPage = stationToStationDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = stationToStationDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("stationToStationList",  stationToStationList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("startStation", startStation);
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("endStation", endStation);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryStationToStationOutputToExcel() { 
        List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStationInfo(startStation,endStation);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StationToStation信息记录"; 
        String[] headers = { "记录编号","起始站","终到站"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<stationToStationList.size();i++) {
        	StationToStation stationToStation = stationToStationList.get(i); 
        	dataset.add(new String[]{stationToStation.getId() + "",stationToStation.getStartStation().getStationName(),
stationToStation.getEndStation().getStationName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"StationToStation.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询StationToStation信息*/
    public String FrontQueryStationToStation() {
        if(currentPage == 0) currentPage = 1;
        List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStationInfo(startStation, endStation, currentPage);
        /*计算总的页数和总的记录数*/
        stationToStationDAO.CalculateTotalPageAndRecordNumber(startStation, endStation);
        /*获取到总的页码数目*/
        totalPage = stationToStationDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = stationToStationDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("stationToStationList",  stationToStationList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("startStation", startStation);
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("endStation", endStation);
        return "front_query_view";
    }

    /*查询要修改的StationToStation信息*/
    public String ModifyStationToStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取StationToStation对象*/
        StationToStation stationToStation = stationToStationDAO.GetStationToStationById(id);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("stationToStation",  stationToStation);
        return "modify_view";
    }

    /*查询要修改的StationToStation信息*/
    public String FrontShowStationToStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键id获取StationToStation对象*/
        StationToStation stationToStation = stationToStationDAO.GetStationToStationById(id);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("stationToStation",  stationToStation);
        return "front_show_view";
    }

    /*更新修改StationToStation信息*/
    public String ModifyStationToStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(stationToStation.getStartStation().getStationId());
            stationToStation.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(stationToStation.getEndStation().getStationId());
            stationToStation.setEndStation(endStation);
            stationToStationDAO.UpdateStationToStation(stationToStation);
            ctx.put("message",  java.net.URLEncoder.encode("StationToStation信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationToStation信息更新失败!"));
            return "error";
       }
   }

    /*删除StationToStation信息*/
    public String DeleteStationToStation() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            stationToStationDAO.DeleteStationToStation(id);
            ctx.put("message",  java.net.URLEncoder.encode("StationToStation删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationToStation删除失败!"));
            return "error";
        }
    }

}
