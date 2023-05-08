package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.BusLineDAO;
import com.mobileserver.domain.BusLine;

import org.json.JSONStringer;

public class BusLineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���칫����·ҵ������*/
	private BusLineDAO busLineDAO = new BusLineDAO();

	/*Ĭ�Ϲ��캯��*/
	public BusLineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ������·�Ĳ�����Ϣ*/
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			int startStation = 0;
			if (request.getParameter("startStation") != null)
				startStation = Integer.parseInt(request.getParameter("startStation"));
			int endStation = 0;
			if (request.getParameter("endStation") != null)
				endStation = Integer.parseInt(request.getParameter("endStation"));
			String company = request.getParameter("company");
			company = company == null ? "" : new String(request.getParameter(
					"company").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�й�����·��ѯ*/
			List<BusLine> busLineList = busLineDAO.QueryBusLine(name,startStation,endStation,company);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<BusLines>").append("\r\n");
			for (int i = 0; i < busLineList.size(); i++) {
				sb.append("	<BusLine>").append("\r\n")
				.append("		<lineId>")
				.append(busLineList.get(i).getLineId())
				.append("</lineId>").append("\r\n")
				.append("		<name>")
				.append(busLineList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<startStation>")
				.append(busLineList.get(i).getStartStation())
				.append("</startStation>").append("\r\n")
				.append("		<endStation>")
				.append(busLineList.get(i).getEndStation())
				.append("</endStation>").append("\r\n")
				.append("		<startTime>")
				.append(busLineList.get(i).getStartTime())
				.append("</startTime>").append("\r\n")
				.append("		<endTime>")
				.append(busLineList.get(i).getEndTime())
				.append("</endTime>").append("\r\n")
				.append("		<company>")
				.append(busLineList.get(i).getCompany())
				.append("</company>").append("\r\n")
				.append("		<tjzd>")
				.append(busLineList.get(i).getTjzd())
				.append("</tjzd>").append("\r\n")
				.append("		<polylinePoints>")
				.append(busLineList.get(i).getPolylinePoints())
				.append("</polylinePoints>").append("\r\n")
				.append("	</BusLine>").append("\r\n");
			}
			sb.append("</BusLines>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(BusLine busLine: busLineList) {
				  stringer.object();
			  stringer.key("lineId").value(busLine.getLineId());
			  stringer.key("name").value(busLine.getName());
			  stringer.key("startStation").value(busLine.getStartStation());
			  stringer.key("endStation").value(busLine.getEndStation());
			  stringer.key("startTime").value(busLine.getStartTime());
			  stringer.key("endTime").value(busLine.getEndTime());
			  stringer.key("company").value(busLine.getCompany());
			  stringer.key("tjzd").value(busLine.getTjzd());
			  stringer.key("polylinePoints").value(busLine.getPolylinePoints());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӹ�����·����ȡ������·�������������浽�½��Ĺ�����·���� */ 
			BusLine busLine = new BusLine();
			int lineId = Integer.parseInt(request.getParameter("lineId"));
			busLine.setLineId(lineId);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			busLine.setName(name);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			busLine.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			busLine.setEndStation(endStation);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			busLine.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			busLine.setEndTime(endTime);
			String company = new String(request.getParameter("company").getBytes("iso-8859-1"), "UTF-8");
			busLine.setCompany(company);
			String tjzd = new String(request.getParameter("tjzd").getBytes("iso-8859-1"), "UTF-8");
			busLine.setTjzd(tjzd);
			String polylinePoints = new String(request.getParameter("polylinePoints").getBytes("iso-8859-1"), "UTF-8");
			busLine.setPolylinePoints(polylinePoints);

			/* ����ҵ���ִ����Ӳ��� */
			String result = busLineDAO.AddBusLine(busLine);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������·����ȡ������·�ļ�¼���*/
			int lineId = Integer.parseInt(request.getParameter("lineId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = busLineDAO.DeleteBusLine(lineId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¹�����·֮ǰ�ȸ���lineId��ѯĳ��������·*/
			int lineId = Integer.parseInt(request.getParameter("lineId"));
			BusLine busLine = busLineDAO.GetBusLine(lineId);

			// �ͻ��˲�ѯ�Ĺ�����·���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("lineId").value(busLine.getLineId());
			  stringer.key("name").value(busLine.getName());
			  stringer.key("startStation").value(busLine.getStartStation());
			  stringer.key("endStation").value(busLine.getEndStation());
			  stringer.key("startTime").value(busLine.getStartTime());
			  stringer.key("endTime").value(busLine.getEndTime());
			  stringer.key("company").value(busLine.getCompany());
			  stringer.key("tjzd").value(busLine.getTjzd());
			  stringer.key("polylinePoints").value(busLine.getPolylinePoints());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¹�����·����ȡ������·�������������浽�½��Ĺ�����·���� */ 
			BusLine busLine = new BusLine();
			int lineId = Integer.parseInt(request.getParameter("lineId"));
			busLine.setLineId(lineId);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			busLine.setName(name);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			busLine.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			busLine.setEndStation(endStation);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			busLine.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			busLine.setEndTime(endTime);
			String company = new String(request.getParameter("company").getBytes("iso-8859-1"), "UTF-8");
			busLine.setCompany(company);
			String tjzd = new String(request.getParameter("tjzd").getBytes("iso-8859-1"), "UTF-8");
			busLine.setTjzd(tjzd);
			String polylinePoints = new String(request.getParameter("polylinePoints").getBytes("iso-8859-1"), "UTF-8");
			busLine.setPolylinePoints(polylinePoints);

			/* ����ҵ���ִ�и��²��� */
			String result = busLineDAO.UpdateBusLine(busLine);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
