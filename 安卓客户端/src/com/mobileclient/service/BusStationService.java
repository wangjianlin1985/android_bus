package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.BusStation;
import com.mobileclient.util.HttpUtil;

/*站点信息管理业务逻辑层*/
public class BusStationService {
	/* 添加站点信息 */
	public String AddBusStation(BusStation busStation) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", busStation.getStationId() + "");
		params.put("stationName", busStation.getStationName());
		params.put("longitude", busStation.getLongitude() + "");
		params.put("latitude", busStation.getLatitude() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询站点信息 */
	public List<BusStation> QueryBusStation(BusStation queryConditionBusStation) throws Exception {
		String urlString = HttpUtil.BASE_URL + "BusStationServlet?action=query";
		if(queryConditionBusStation != null) {
			urlString += "&stationName=" + URLEncoder.encode(queryConditionBusStation.getStationName(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		BusStationListHandler busStationListHander = new BusStationListHandler();
		xr.setContentHandler(busStationListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<BusStation> busStationList = busStationListHander.getBusStationList();
		return busStationList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<BusStation> busStationList = new ArrayList<BusStation>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BusStation busStation = new BusStation();
				busStation.setStationId(object.getInt("stationId"));
				busStation.setStationName(object.getString("stationName"));
				busStation.setLongitude((float) object.getDouble("longitude"));
				busStation.setLatitude((float) object.getDouble("latitude"));
				busStationList.add(busStation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return busStationList;
	}

	/* 更新站点信息 */
	public String UpdateBusStation(BusStation busStation) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", busStation.getStationId() + "");
		params.put("stationName", busStation.getStationName());
		params.put("longitude", busStation.getLongitude() + "");
		params.put("latitude", busStation.getLatitude() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除站点信息 */
	public String DeleteBusStation(int stationId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", stationId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "站点信息信息删除失败!";
		}
	}

	/* 根据记录编号获取站点信息对象 */
	public BusStation GetBusStation(int stationId)  {
		List<BusStation> busStationList = new ArrayList<BusStation>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stationId", stationId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BusStationServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BusStation busStation = new BusStation();
				busStation.setStationId(object.getInt("stationId"));
				busStation.setStationName(object.getString("stationName"));
				busStation.setLongitude((float) object.getDouble("longitude"));
				busStation.setLatitude((float) object.getDouble("latitude"));
				busStationList.add(busStation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = busStationList.size();
		if(size>0) return busStationList.get(0); 
		else return null; 
	}
}
