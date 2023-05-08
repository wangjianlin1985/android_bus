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

    /*�������Ҫ��ѯ������: ��ʼվ*/
    private BusStation startStation;
    public void setStartStation(BusStation startStation) {
        this.startStation = startStation;
    }
    public BusStation getStartStation() {
        return this.startStation;
    }

    /*�������Ҫ��ѯ������: �յ�վ*/
    private BusStation endStation;
    public void setEndStation(BusStation endStation) {
        this.endStation = endStation;
    }
    public BusStation getEndStation() {
        return this.endStation;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource BusStationDAO busStationDAO;
    @Resource StationToStationDAO stationToStationDAO;

    /*��������StationToStation����*/
    private StationToStation stationToStation;
    public void setStationToStation(StationToStation stationToStation) {
        this.stationToStation = stationToStation;
    }
    public StationToStation getStationToStation() {
        return this.stationToStation;
    }

    /*��ת�����StationToStation��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�BusStation��Ϣ*/
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        return "add_view";
    }

    /*���StationToStation��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddStationToStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(stationToStation.getStartStation().getStationId());
            stationToStation.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(stationToStation.getEndStation().getStationId());
            stationToStation.setEndStation(endStation);
            stationToStationDAO.AddStationToStation(stationToStation);
            ctx.put("message",  java.net.URLEncoder.encode("StationToStation��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationToStation���ʧ��!"));
            return "error";
        }
    }

    /*��ѯStationToStation��Ϣ*/
    public String QueryStationToStation() {
        if(currentPage == 0) currentPage = 1;
        List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStationInfo(startStation, endStation, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        stationToStationDAO.CalculateTotalPageAndRecordNumber(startStation, endStation);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = stationToStationDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryStationToStationOutputToExcel() { 
        List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStationInfo(startStation,endStation);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StationToStation��Ϣ��¼"; 
        String[] headers = { "��¼���","��ʼվ","�յ�վ"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"StationToStation.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯStationToStation��Ϣ*/
    public String FrontQueryStationToStation() {
        if(currentPage == 0) currentPage = 1;
        List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStationInfo(startStation, endStation, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        stationToStationDAO.CalculateTotalPageAndRecordNumber(startStation, endStation);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = stationToStationDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�StationToStation��Ϣ*/
    public String ModifyStationToStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡStationToStation����*/
        StationToStation stationToStation = stationToStationDAO.GetStationToStationById(id);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("stationToStation",  stationToStation);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�StationToStation��Ϣ*/
    public String FrontShowStationToStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������id��ȡStationToStation����*/
        StationToStation stationToStation = stationToStationDAO.GetStationToStationById(id);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("stationToStation",  stationToStation);
        return "front_show_view";
    }

    /*�����޸�StationToStation��Ϣ*/
    public String ModifyStationToStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(stationToStation.getStartStation().getStationId());
            stationToStation.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(stationToStation.getEndStation().getStationId());
            stationToStation.setEndStation(endStation);
            stationToStationDAO.UpdateStationToStation(stationToStation);
            ctx.put("message",  java.net.URLEncoder.encode("StationToStation��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationToStation��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��StationToStation��Ϣ*/
    public String DeleteStationToStation() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            stationToStationDAO.DeleteStationToStation(id);
            ctx.put("message",  java.net.URLEncoder.encode("StationToStationɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StationToStationɾ��ʧ��!"));
            return "error";
        }
    }

}
