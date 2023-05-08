package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.StationToStation;
import com.mobileclient.util.HttpUtil;

/*站站查询管理业务逻辑层*/
public class StationToStationService {
	/* 添加站站查询 */
	public String AddStationToStation(StationToStation stationToStation) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", stationToStation.getId() + "");
		params.put("startStation", stationToStation.getStartStation() + "");
		params.put("endStation", stationToStation.getEndStation() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationToStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询站站查询 */
	public List<StationToStation> QueryStationToStation(StationToStation queryConditionStationToStation) throws Exception {
		String urlString = HttpUtil.BASE_URL + "StationToStationServlet?action=query";
		if(queryConditionStationToStation != null) {
			urlString += "&startStation=" + queryConditionStationToStation.getStartStation();
			urlString += "&endStation=" + queryConditionStationToStation.getEndStation();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		StationToStationListHandler stationToStationListHander = new StationToStationListHandler();
		xr.setContentHandler(stationToStationListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<StationToStation> stationToStationList = stationToStationListHander.getStationToStationList();
		return stationToStationList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<StationToStation> stationToStationList = new ArrayList<StationToStation>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				StationToStation stationToStation = new StationToStation();
				stationToStation.setId(object.getInt("id"));
				stationToStation.setStartStation(object.getInt("startStation"));
				stationToStation.setEndStation(object.getInt("endStation"));
				stationToStationList.add(stationToStation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stationToStationList;
	}

	/* 更新站站查询 */
	public String UpdateStationToStation(StationToStation stationToStation) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", stationToStation.getId() + "");
		params.put("startStation", stationToStation.getStartStation() + "");
		params.put("endStation", stationToStation.getEndStation() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationToStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除站站查询 */
	public String DeleteStationToStation(int id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationToStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "站站查询信息删除失败!";
		}
	}

	/* 根据记录编号获取站站查询对象 */
	public StationToStation GetStationToStation(int id)  {
		List<StationToStation> stationToStationList = new ArrayList<StationToStation>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "StationToStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				StationToStation stationToStation = new StationToStation();
				stationToStation.setId(object.getInt("id"));
				stationToStation.setStartStation(object.getInt("startStation"));
				stationToStation.setEndStation(object.getInt("endStation"));
				stationToStationList.add(stationToStation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = stationToStationList.size();
		if(size>0) return stationToStationList.get(0); 
		else return null; 
	}
}
