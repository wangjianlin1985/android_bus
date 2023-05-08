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

    /*�������Ҫ��ѯ������: ��·����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ���վ*/
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

    /*�������Ҫ��ѯ������: ������˾*/
    private String company;
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return this.company;
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

    private int lineId;
    public void setLineId(int lineId) {
        this.lineId = lineId;
    }
    public int getLineId() {
        return lineId;
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
    @Resource BusLineDAO busLineDAO;

    /*��������BusLine����*/
    private BusLine busLine;
    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }
    public BusLine getBusLine() {
        return this.busLine;
    }

    /*��ת�����BusLine��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�BusStation��Ϣ*/
        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        return "add_view";
    }

    /*���BusLine��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddBusLine() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(busLine.getStartStation().getStationId());
            busLine.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(busLine.getEndStation().getStationId());
            busLine.setEndStation(endStation);
            busLineDAO.AddBusLine(busLine);
            ctx.put("message",  java.net.URLEncoder.encode("BusLine��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusLine���ʧ��!"));
            return "error";
        }
    }

    /*��ѯBusLine��Ϣ*/
    public String QueryBusLine() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(company == null) company = "";
        List<BusLine> busLineList = busLineDAO.QueryBusLineInfo(name, startStation, endStation, company, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        busLineDAO.CalculateTotalPageAndRecordNumber(name, startStation, endStation, company);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = busLineDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryBusLineOutputToExcel() { 
        if(name == null) name = "";
        if(company == null) company = "";
        List<BusLine> busLineList = busLineDAO.QueryBusLineInfo(name,startStation,endStation,company);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BusLine��Ϣ��¼"; 
        String[] headers = { "��·����","���վ","�յ�վ","�װ೵ʱ��","ĩ�೵ʱ��","������˾"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"BusLine.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯBusLine��Ϣ*/
    public String FrontQueryBusLine() {
        if(currentPage == 0) currentPage = 1;
        if(name == null) name = "";
        if(company == null) company = "";
        List<BusLine> busLineList = busLineDAO.QueryBusLineInfo(name, startStation, endStation, company, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        busLineDAO.CalculateTotalPageAndRecordNumber(name, startStation, endStation, company);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = busLineDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�BusLine��Ϣ*/
    public String ModifyBusLineQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������lineId��ȡBusLine����*/
        BusLine busLine = busLineDAO.GetBusLineByLineId(lineId);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("busLine",  busLine);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�BusLine��Ϣ*/
    public String FrontShowBusLineQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������lineId��ȡBusLine����*/
        BusLine busLine = busLineDAO.GetBusLineByLineId(lineId);

        List<BusStation> busStationList = busStationDAO.QueryAllBusStationInfo();
        ctx.put("busStationList", busStationList);
        ctx.put("busLine",  busLine);
        return "front_show_view";
    }

    /*�����޸�BusLine��Ϣ*/
    public String ModifyBusLine() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BusStation startStation = busStationDAO.GetBusStationByStationId(busLine.getStartStation().getStationId());
            busLine.setStartStation(startStation);
            BusStation endStation = busStationDAO.GetBusStationByStationId(busLine.getEndStation().getStationId());
            busLine.setEndStation(endStation);
            busLineDAO.UpdateBusLine(busLine);
            ctx.put("message",  java.net.URLEncoder.encode("BusLine��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusLine��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��BusLine��Ϣ*/
    public String DeleteBusLine() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            busLineDAO.DeleteBusLine(lineId);
            ctx.put("message",  java.net.URLEncoder.encode("BusLineɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BusLineɾ��ʧ��!"));
            return "error";
        }
    }

}
