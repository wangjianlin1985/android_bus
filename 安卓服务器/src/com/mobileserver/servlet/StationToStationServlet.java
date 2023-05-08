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

	/*构造站站查询业务层对象*/
	private StationToStationDAO stationToStationDAO = new StationToStationDAO();

	/*默认构造函数*/
	public StationToStationServlet() {
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
			/*获取查询站站查询的参数信息*/
			int startStation = 0;
			if (request.getParameter("startStation") != null)
				startStation = Integer.parseInt(request.getParameter("startStation"));
			int endStation = 0;
			if (request.getParameter("endStation") != null)
				endStation = Integer.parseInt(request.getParameter("endStation"));

			/*调用业务逻辑层执行站站查询查询*/
			List<StationToStation> stationToStationList = stationToStationDAO.QueryStationToStation(startStation,endStation);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加站站查询：获取站站查询参数，参数保存到新建的站站查询对象 */ 
			StationToStation stationToStation = new StationToStation();
			int id = Integer.parseInt(request.getParameter("id"));
			stationToStation.setId(id);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			stationToStation.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			stationToStation.setEndStation(endStation);

			/* 调用业务层执行添加操作 */
			String result = stationToStationDAO.AddStationToStation(stationToStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除站站查询：获取站站查询的记录编号*/
			int id = Integer.parseInt(request.getParameter("id"));
			/*调用业务逻辑层执行删除操作*/
			String result = stationToStationDAO.DeleteStationToStation(id);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新站站查询之前先根据id查询某个站站查询*/
			int id = Integer.parseInt(request.getParameter("id"));
			StationToStation stationToStation = stationToStationDAO.GetStationToStation(id);

			// 客户端查询的站站查询对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新站站查询：获取站站查询参数，参数保存到新建的站站查询对象 */ 
			StationToStation stationToStation = new StationToStation();
			int id = Integer.parseInt(request.getParameter("id"));
			stationToStation.setId(id);
			int startStation = Integer.parseInt(request.getParameter("startStation"));
			stationToStation.setStartStation(startStation);
			int endStation = Integer.parseInt(request.getParameter("endStation"));
			stationToStation.setEndStation(endStation);

			/* 调用业务层执行更新操作 */
			String result = stationToStationDAO.UpdateStationToStation(stationToStation);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
