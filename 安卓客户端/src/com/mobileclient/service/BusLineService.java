package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.BusLine;
import com.mobileclient.util.HttpUtil;

/*公交线路管理业务逻辑层*/
public class BusLineService {
	/* 添加公交线路 */
	public String AddBusLine(BusLine busLine) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lineId", busLine.getLineId() + "");
		params.put("name", busLine.getName());
		params.put("startStation", busLine.getStartStation() + "");
		params.put("endStation", busLine.getEndStation() + "");
		params.put("startTime", busLine.getStartTime());
		params.put("endTime", busLine.getEndTime());
		params.put("company", busLine.getCompany());
		params.put("tjzd", busLine.getTjzd());
		params.put("polylinePoints", busLine.getPolylinePoints());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusLineServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询公交线路 */
	public List<BusLine> QueryBusLine(BusLine queryConditionBusLine) throws Exception {
		String urlString = HttpUtil.BASE_URL + "BusLineServlet?action=query";
		if(queryConditionBusLine != null) {
			urlString += "&name=" + URLEncoder.encode(queryConditionBusLine.getName(), "UTF-8") + "";
			urlString += "&startStation=" + queryConditionBusLine.getStartStation();
			urlString += "&endStation=" + queryConditionBusLine.getEndStation();
			urlString += "&company=" + URLEncoder.encode(queryConditionBusLine.getCompany(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		BusLineListHandler busLineListHander = new BusLineListHandler();
		xr.setContentHandler(busLineListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<BusLine> busLineList = busLineListHander.getBusLineList();
		return busLineList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<BusLine> busLineList = new ArrayList<BusLine>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BusLine busLine = new BusLine();
				busLine.setLineId(object.getInt("lineId"));
				busLine.setName(object.getString("name"));
				busLine.setStartStation(object.getInt("startStation"));
				busLine.setEndStation(object.getInt("endStation"));
				busLine.setStartTime(object.getString("startTime"));
				busLine.setEndTime(object.getString("endTime"));
				busLine.setCompany(object.getString("company"));
				busLine.setTjzd(object.getString("tjzd"));
				busLine.setPolylinePoints(object.getString("polylinePoints"));
				busLineList.add(busLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return busLineList;
	}

	/* 更新公交线路 */
	public String UpdateBusLine(BusLine busLine) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lineId", busLine.getLineId() + "");
		params.put("name", busLine.getName());
		params.put("startStation", busLine.getStartStation() + "");
		params.put("endStation", busLine.getEndStation() + "");
		params.put("startTime", busLine.getStartTime());
		params.put("endTime", busLine.getEndTime());
		params.put("company", busLine.getCompany());
		params.put("tjzd", busLine.getTjzd());
		params.put("polylinePoints", busLine.getPolylinePoints());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusLineServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除公交线路 */
	public String DeleteBusLine(int lineId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lineId", lineId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusLineServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "公交线路信息删除失败!";
		}
	}

	/* 根据记录编号获取公交线路对象 */
	public BusLine GetBusLine(int lineId)  {
		List<BusLine> busLineList = new ArrayList<BusLine>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("lineId", lineId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusLineServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BusLine busLine = new BusLine();
				busLine.setLineId(object.getInt("lineId"));
				busLine.setName(object.getString("name"));
				busLine.setStartStation(object.getInt("startStation"));
				busLine.setEndStation(object.getInt("endStation"));
				busLine.setStartTime(object.getString("startTime"));
				busLine.setEndTime(object.getString("endTime"));
				busLine.setCompany(object.getString("company"));
				busLine.setTjzd(object.getString("tjzd"));
				busLine.setPolylinePoints(object.getString("polylinePoints"));
				busLineList.add(busLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = busLineList.size();
		if(size>0) return busLineList.get(0); 
		else return null; 
	}
}
