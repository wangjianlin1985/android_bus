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

	/*构造站点信息业务层对象*/
	private BusStationDAO busStationDAO = new BusStationDAO();

	/*默认构造函数*/
	public BusStationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询站点信息的参数信息*/
			String stationName = request.getParameter("stationName");
			stationName = stationName == null ? "" : new String(request.getParameter(
					"stationName").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行站点信息查询*/
			List<BusStation> busStationList = busStationDAO.QueryBusStation(stationName);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加站点信息：获取站点信息参数，参数保存到新建的站点信息对象 */ 
			BusStation busStation = new BusStation();
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			busStation.setStationId(stationId);
			String stationName = new String(request.getParameter("stationName").getBytes("iso-8859-1"), "UTF-8");
			busStation.setStationName(stationName);
			float longitude = Float.parseFloat(request.getParameter("longitude"));
			busStation.setLongitude(longitude);
			float latitude = Float.parseFloat(request.getParameter("latitude"));
			busStation.setLatitude(latitude);

			/* 调用业务层执行添加操作 */
			String result = busStationDAO.AddBusStation(busStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除站点信息：获取站点信息的记录编号*/
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			/*调用业务逻辑层执行删除操作*/
			String result = busStationDAO.DeleteBusStation(stationId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新站点信息之前先根据stationId查询某个站点信息*/
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			BusStation busStation = busStationDAO.GetBusStation(stationId);

			// 客户端查询的站点信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新站点信息：获取站点信息参数，参数保存到新建的站点信息对象 */ 
			BusStation busStation = new BusStation();
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			busStation.setStationId(stationId);
			String stationName = new String(request.getParameter("stationName").getBytes("iso-8859-1"), "UTF-8");
			busStation.setStationName(stationName);
			float longitude = Float.parseFloat(request.getParameter("longitude"));
			busStation.setLongitude(longitude);
			float latitude = Float.parseFloat(request.getParameter("latitude"));
			busStation.setLatitude(latitude);

			/* 调用业务层执行更新操作 */
			String result = busStationDAO.UpdateBusStation(busStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
