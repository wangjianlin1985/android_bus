package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.StationToStation;
import com.mobileserver.util.DB;

public class StationToStationDAO {

	public List<StationToStation> QueryStationToStation(int startStation,int endStation) {
		List<StationToStation> stationToStationList = new ArrayList<StationToStation>();
		DB db = new DB();
		String sql = "select * from StationToStation where 1=1";
		if (startStation != 0)
			sql += " and startStation=" + startStation;
		if (endStation != 0)
			sql += " and endStation=" + endStation;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				StationToStation stationToStation = new StationToStation();
				stationToStation.setId(rs.getInt("id"));
				stationToStation.setStartStation(rs.getInt("startStation"));
				stationToStation.setEndStation(rs.getInt("endStation"));
				stationToStationList.add(stationToStation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return stationToStationList;
	}
	/* 传入站站查询对象，进行站站查询的添加业务 */
	public String AddStationToStation(StationToStation stationToStation) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新站站查询 */
			String sqlString = "insert into StationToStation(startStation,endStation) values (";
			sqlString += stationToStation.getStartStation() + ",";
			sqlString += stationToStation.getEndStation() ;
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "站站查询添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站站查询添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除站站查询 */
	public String DeleteStationToStation(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from StationToStation where id=" + id;
			db.executeUpdate(sqlString);
			result = "站站查询删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站站查询删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到站站查询 */
	public StationToStation GetStationToStation(int id) {
		StationToStation stationToStation = null;
		DB db = new DB();
		String sql = "select * from StationToStation where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				stationToStation = new StationToStation();
				stationToStation.setId(rs.getInt("id"));
				stationToStation.setStartStation(rs.getInt("startStation"));
				stationToStation.setEndStation(rs.getInt("endStation"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return stationToStation;
	}
	/* 更新站站查询 */
	public String UpdateStationToStation(StationToStation stationToStation) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update StationToStation set ";
			sql += "startStation=" + stationToStation.getStartStation() + ",";
			sql += "endStation=" + stationToStation.getEndStation();
			sql += " where id=" + stationToStation.getId();
			db.executeUpdate(sql);
			result = "站站查询更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站站查询更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
