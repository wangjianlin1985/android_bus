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

    /*�������Ҫ��ѯ������: վ������*/
    private String stationName;
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public String getStationName() {
        return this.stationName;
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

    private int stationId;
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }
    public int getStationId() {
        return stationId;
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

    /*��������BusStation����*/
    private BusStation busStation;
    public void setBusStation(BusStation busStation) {
        this.busStation = busStation;
    }
    public BusStation getBusStation() {
        return this.busStation;
    }

    /*��ת�����BusStation��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���BusStation��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddBusStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            busStationDAO.AddBusStation(busStation);
            ctx.put("message",  java.net.URLEncoder.encode("BusStation��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusStation���ʧ��!"));
            return "error";
        }
    }

    /*��ѯBusStation��Ϣ*/
    public String QueryBusStation() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        List<BusStation> busStationList = busStationDAO.QueryBusStationInfo(stationName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        busStationDAO.CalculateTotalPageAndRecordNumber(stationName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = busStationDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = busStationDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("busStationList",  busStationList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stationName", stationName);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryBusStationOutputToExcel() { 
        if(stationName == null) stationName = "";
        List<BusStation> busStationList = busStationDAO.QueryBusStationInfo(stationName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BusStation��Ϣ��¼"; 
        String[] headers = { "��¼���","վ������","����","γ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"BusStation.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯBusStation��Ϣ*/
    public String FrontQueryBusStation() {
        if(currentPage == 0) currentPage = 1;
        if(stationName == null) stationName = "";
        List<BusStation> busStationList = busStationDAO.QueryBusStationInfo(stationName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        busStationDAO.CalculateTotalPageAndRecordNumber(stationName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = busStationDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = busStationDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("busStationList",  busStationList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("stationName", stationName);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�BusStation��Ϣ*/
    public String ModifyBusStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stationId��ȡBusStation����*/
        BusStation busStation = busStationDAO.GetBusStationByStationId(stationId);

        ctx.put("busStation",  busStation);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�BusStation��Ϣ*/
    public String FrontShowBusStationQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stationId��ȡBusStation����*/
        BusStation busStation = busStationDAO.GetBusStationByStationId(stationId);

        ctx.put("busStation",  busStation);
        return "front_show_view";
    }

    /*�����޸�BusStation��Ϣ*/
    public String ModifyBusStation() {
        ActionContext ctx = ActionContext.getContext();
        try {
            busStationDAO.UpdateBusStation(busStation);
            ctx.put("message",  java.net.URLEncoder.encode("BusStation��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusStation��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��BusStation��Ϣ*/
    public String DeleteBusStation() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            busStationDAO.DeleteBusStation(stationId);
            ctx.put("message",  java.net.URLEncoder.encode("BusStationɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusStationɾ��ʧ��!"));
            return "error";
        }
    }

}
