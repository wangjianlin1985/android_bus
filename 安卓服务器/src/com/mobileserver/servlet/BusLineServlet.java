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

	/*构造公交线路业务层对象*/
	private BusLineDAO busLineDAO = new BusLineDAO();

	/*默认构造函数*/
	public BusLineServlet() {
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
			/*获取查询公交线路的参数信息*/
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

			/*调用业务逻辑层执行公交线路查询*/
			List<BusLine> busLineList = busLineDAO.QueryBusLine(name,startStation,endStation,company);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加公交线路：获取公交线路参数，参数保存到新建的公交线路对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = busLineDAO.AddBusLine(busLine);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除公交线路：获取公交线路的记录编号*/
			int lineId = Integer.parseInt(request.getParameter("lineId"));
			/*调用业务逻辑层执行删除操作*/
			String result = busLineDAO.DeleteBusLine(lineId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新公交线路之前先根据lineId查询某个公交线路*/
			int lineId = Integer.parseInt(request.getParameter("lineId"));
			BusLine busLine = busLineDAO.GetBusLine(lineId);

			// 客户端查询的公交线路对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新公交线路：获取公交线路参数，参数保存到新建的公交线路对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = busLineDAO.UpdateBusLine(busLine);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
