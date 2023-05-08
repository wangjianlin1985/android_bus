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
	/* ����վվ��ѯ���󣬽���վվ��ѯ�����ҵ�� */
	public String AddStationToStation(StationToStation stationToStation) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����վվ��ѯ */
			String sqlString = "insert into StationToStation(startStation,endStation) values (";
			sqlString += stationToStation.getStartStation() + ",";
			sqlString += stationToStation.getEndStation() ;
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "վվ��ѯ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վվ��ѯ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��վվ��ѯ */
	public String DeleteStationToStation(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from StationToStation where id=" + id;
			db.executeUpdate(sqlString);
			result = "վվ��ѯɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վվ��ѯɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��վվ��ѯ */
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
	/* ����վվ��ѯ */
	public String UpdateStationToStation(StationToStation stationToStation) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update StationToStation set ";
			sql += "startStation=" + stationToStation.getStartStation() + ",";
			sql += "endStation=" + stationToStation.getEndStation();
			sql += " where id=" + stationToStation.getId();
			db.executeUpdate(sql);
			result = "վվ��ѯ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "վվ��ѯ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
