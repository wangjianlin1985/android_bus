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
	/* ����վ����Ϣ���󣬽���վ����Ϣ�����ҵ�� */
	public String AddBusStation(BusStation busStation) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����վ����Ϣ */
			String sqlString = "insert into BusStation(stationName,longitude,latitude) values (";
			sqlString += "'" + busStation.getStationName() + "',";
			sqlString += busStation.getLongitude() + ",";
			sqlString += busStation.getLatitude();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "վ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��վ����Ϣ */
	public String DeleteBusStation(int stationId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from BusStation where stationId=" + stationId;
			db.executeUpdate(sqlString);
			result = "վ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��վ����Ϣ */
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
	/* ����վ����Ϣ */
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
			result = "վ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
