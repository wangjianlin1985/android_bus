package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.BusStation;
import com.mobileserver.util.DB;

public class BusStationDAO {

	public List<BusStation> QueryBusStation(String stationName) {
		List<BusStation> busStationList = new ArrayList<BusStation>();
		DB db = new DB();
		String sql = "select * from BusStation where 1=1";
		if (!stationName.equals(""))
			sql += " and stationName like '%" + stationName + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				BusStation busStation = new BusStation();
				busStation.setStationId(rs.getInt("stationId"));
				busStation.setStationName(rs.getString("stationName"));
				busStation.setLongitude(rs.getFloat("longitude"));
				busStation.setLatitude(rs.getFloat("latitude"));
				busStationList.add(busStation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return busStationList;
	}
	/* 传入站点信息对象，进行站点信息的添加业务 */
	public String AddBusStation(BusStation busStation) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新站点信息 */
			String sqlString = "insert into BusStation(stationName,longitude,latitude) values (";
			sqlString += "'" + busStation.getStationName() + "',";
			sqlString += busStation.getLongitude() + ",";
			sqlString += busStation.getLatitude();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "站点信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站点信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除站点信息 */
	public String DeleteBusStation(int stationId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from BusStation where stationId=" + stationId;
			db.executeUpdate(sqlString);
			result = "站点信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站点信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到站点信息 */
	public BusStation GetBusStation(int stationId) {
		BusStation busStation = null;
		DB db = new DB();
		String sql = "select * from BusStation where stationId=" + stationId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				busStation = new BusStation();
				busStation.setStationId(rs.getInt("stationId"));
				busStation.setStationName(rs.getString("stationName"));
				busStation.setLongitude(rs.getFloat("longitude"));
				busStation.setLatitude(rs.getFloat("latitude"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return busStation;
	}
	/* 更新站点信息 */
	public String UpdateBusStation(BusStation busStation) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update BusStation set ";
			sql += "stationName='" + busStation.getStationName() + "',";
			sql += "longitude=" + busStation.getLongitude() + ",";
			sql += "latitude=" + busStation.getLatitude();
			sql += " where stationId=" + busStation.getStationId();
			db.executeUpdate(sql);
			result = "站点信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "站点信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
