package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.StationToStationDAO;
import com.mobileserver.domain.StationToStation;

import org.json.JSONStringer;

public class StationToStationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����վվ��ѯҵ������*/
	private StationToStationDAO stationToStationDAO = new StationToStationDAO();

	/*Ĭ�Ϲ��캯��*/
	public StationToStationServlet() {
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
			/*��ȡ��ѯվվ��ѯ�Ĳ�����Ϣ*/
			int startStation = 0;
			if (request.getParameter("startStation") != null)
				startStation = Integer.parseInt(request.getParameter("startStation"));
			int endStation = 0;
			if (request.getParameter("endStation") != null)
				endStation = Integer.parseInt(request.getParameter("endStation"));

			/*����ҵ���߼���ִ��վվ��ѯ��ѯ*/
			List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStation(startStation,endStation);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<StationToStations>").append("\r\n");
			for (int i = 0; i < stationToStationList.size(); i++) {
				sb.append("	<StationToStation>").append("\r\n")
				.append("		<id>")
				.append(stationToStationList.get(i).getId())
				.append("</id>").append("\r\n")
				.append("		<startStation>")
				.append(stationToStationList.get(i).getStartStation())
				.append("</startStation>").append("\r\n")
				.append("		<endStation>")
				.append(stationToStationList.get(i).getEndStation())
				.append("</endStation>").append("\r\n")
				.append("	</StationToStation>").append("\r\n");
			}
			sb.append("</StationToStations>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(StationToStation stationToStation: stationToStationList) {
				  stringer.object();
			  stringer.key("id").value(stationToStation.getId());
			  stringer.key("startStation").value(stationToStation.getStartStation());
			  stringer.key("endStation").value(stationToStation.getEndStation());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���վվ��ѯ����ȡվվ��ѯ�������������浽�½���վվ��ѯ���� */ 
			StationToStation stationToStation = new StationToStation();
			int id = Integer.parseInt(request.getParameter("id"));
			stationToStation.setId(id);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			stationToStation.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			stationToStation.setEndStation(endStation);

			/* ����ҵ���ִ����Ӳ��� */
			String result = stationToStationDAO.AddStationToStation(stationToStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��վվ��ѯ����ȡվվ��ѯ�ļ�¼���*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = stationToStationDAO.DeleteStationToStation(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����վվ��ѯ֮ǰ�ȸ���id��ѯĳ��վվ��ѯ*/
			int id = Integer.parseInt(request.getParameter("id"));
			StationToStation stationToStation = stationToStationDAO.GetStationToStation(id);

			// �ͻ��˲�ѯ��վվ��ѯ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("id").value(stationToStation.getId());
			  stringer.key("startStation").value(stationToStation.getStartStation());
			  stringer.key("endStation").value(stationToStation.getEndStation());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����վվ��ѯ����ȡվվ��ѯ�������������浽�½���վվ��ѯ���� */ 
			StationToStation stationToStation = new StationToStation();
			int id = Integer.parseInt(request.getParameter("id"));
			stationToStation.setId(id);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			stationToStation.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			stationToStation.setEndStation(endStation);

			/* ����ҵ���ִ�и��²��� */
			String result = stationToStationDAO.UpdateStationToStation(stationToStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
