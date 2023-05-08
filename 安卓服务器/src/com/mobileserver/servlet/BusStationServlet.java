package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.BusStationDAO;
import com.mobileserver.domain.BusStation;

import org.json.JSONStringer;

public class BusStationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����վ����Ϣҵ������*/
	private BusStationDAO busStationDAO = new BusStationDAO();

	/*Ĭ�Ϲ��캯��*/
	public BusStationServlet() {
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
			/*��ȡ��ѯվ����Ϣ�Ĳ�����Ϣ*/
			String stationName = request.getParameter("stationName");
			stationName = stationName == null ? "" : new String(request.getParameter(
					"stationName").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��վ����Ϣ��ѯ*/
			List<BusStation> busStationList = busStationDAO.QueryBusStation(stationName);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<BusStations>").append("\r\n");
			for (int i = 0; i < busStationList.size(); i++) {
				sb.append("	<BusStation>").append("\r\n")
				.append("		<stationId>")
				.append(busStationList.get(i).getStationId())
				.append("</stationId>").append("\r\n")
				.append("		<stationName>")
				.append(busStationList.get(i).getStationName())
				.append("</stationName>").append("\r\n")
				.append("		<longitude>")
				.append(busStationList.get(i).getLongitude())
				.append("</longitude>").append("\r\n")
				.append("		<latitude>")
				.append(busStationList.get(i).getLatitude())
				.append("</latitude>").append("\r\n")
				.append("	</BusStation>").append("\r\n");
			}
			sb.append("</BusStations>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(BusStation busStation: busStationList) {
				  stringer.object();
			  stringer.key("stationId").value(busStation.getStationId());
			  stringer.key("stationName").value(busStation.getStationName());
			  stringer.key("longitude").value(busStation.getLongitude());
			  stringer.key("latitude").value(busStation.getLatitude());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���վ����Ϣ����ȡվ����Ϣ�������������浽�½���վ����Ϣ���� */ 
			BusStation busStation = new BusStation();
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			busStation.setStationId(stationId);
			String stationName = new String(request.getParameter("stationName").getBytes("iso-8859-1"), "UTF-8");
			busStation.setStationName(stationName);
			float longitude = Float.parseFloat(request.getParameter("longitude"));
			busStation.setLongitude(longitude);
			float latitude = Float.parseFloat(request.getParameter("latitude"));
			busStation.setLatitude(latitude);

			/* ����ҵ���ִ����Ӳ��� */
			String result = busStationDAO.AddBusStation(busStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��վ����Ϣ����ȡվ����Ϣ�ļ�¼���*/
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = busStationDAO.DeleteBusStation(stationId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����վ����Ϣ֮ǰ�ȸ���stationId��ѯĳ��վ����Ϣ*/
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			BusStation busStation = busStationDAO.GetBusStation(stationId);

			// �ͻ��˲�ѯ��վ����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("stationId").value(busStation.getStationId());
			  stringer.key("stationName").value(busStation.getStationName());
			  stringer.key("longitude").value(busStation.getLongitude());
			  stringer.key("latitude").value(busStation.getLatitude());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����վ����Ϣ����ȡվ����Ϣ�������������浽�½���վ����Ϣ���� */ 
			BusStation busStation = new BusStation();
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			busStation.setStationId(stationId);
			String stationName = new String(request.getParameter("stationName").getBytes("iso-8859-1"), "UTF-8");
			busStation.setStationName(stationName);
			float longitude = Float.parseFloat(request.getParameter("longitude"));
			busStation.setLongitude(longitude);
			float latitude = Float.parseFloat(request.getParameter("latitude"));
			busStation.setLatitude(latitude);

			/* ����ҵ���ִ�и��²��� */
			String result = busStationDAO.UpdateBusStation(busStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
