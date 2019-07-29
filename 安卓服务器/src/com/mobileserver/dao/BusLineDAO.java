package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.BusLine;
import com.mobileserver.util.DB;

public class BusLineDAO {

	public List<BusLine> QueryBusLine(String name,int startStation,int endStation,String company) {
		List<BusLine> busLineList = new ArrayList<BusLine>();
		DB db = new DB();
		String sql = "select * from BusLine where 1=1";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if (startStation != 0)
			sql += " and startStation=" + startStation;
		if (endStation != 0)
			sql += " and endStation=" + endStation;
		if (!company.equals(""))
			sql += " and company like '%" + company + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				BusLine busLine = new BusLine();
				busLine.setLineId(rs.getInt("lineId"));
				busLine.setName(rs.getString("name"));
				busLine.setStartStation(rs.getInt("startStation"));
				busLine.setEndStation(rs.getInt("endStation"));
				busLine.setStartTime(rs.getString("startTime"));
				busLine.setEndTime(rs.getString("endTime"));
				busLine.setCompany(rs.getString("company"));
				busLine.setTjzd(rs.getString("tjzd"));
				busLine.setPolylinePoints(rs.getString("polylinePoints"));
				busLineList.add(busLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return busLineList;
	}
	/* 传入公交线路对象，进行公交线路的添加业务 */
	public String AddBusLine(BusLine busLine) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新公交线路 */
			String sqlString = "insert into BusLine(name,startStation,endStation,startTime,endTime,company,tjzd,polylinePoints) values (";
			sqlString += "'" + busLine.getName() + "',";
			sqlString += busLine.getStartStation() + ",";
			sqlString += busLine.getEndStation() + ",";
			sqlString += "'" + busLine.getStartTime() + "',";
			sqlString += "'" + busLine.getEndTime() + "',";
			sqlString += "'" + busLine.getCompany() + "',";
			sqlString += "'" + busLine.getTjzd() + "',";
			sqlString += "'" + busLine.getPolylinePoints() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "公交线路添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "公交线路添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除公交线路 */
	public String DeleteBusLine(int lineId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from BusLine where lineId=" + lineId;
			db.executeUpdate(sqlString);
			result = "公交线路删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "公交线路删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到公交线路 */
	public BusLine GetBusLine(int lineId) {
		BusLine busLine = null;
		DB db = new DB();
		String sql = "select * from BusLine where lineId=" + lineId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				busLine = new BusLine();
				busLine.setLineId(rs.getInt("lineId"));
				busLine.setName(rs.getString("name"));
				busLine.setStartStation(rs.getInt("startStation"));
				busLine.setEndStation(rs.getInt("endStation"));
				busLine.setStartTime(rs.getString("startTime"));
				busLine.setEndTime(rs.getString("endTime"));
				busLine.setCompany(rs.getString("company"));
				busLine.setTjzd(rs.getString("tjzd"));
				busLine.setPolylinePoints(rs.getString("polylinePoints"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return busLine;
	}
	/* 更新公交线路 */
	public String UpdateBusLine(BusLine busLine) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update BusLine set ";
			sql += "name='" + busLine.getName() + "',";
			sql += "startStation=" + busLine.getStartStation() + ",";
			sql += "endStation=" + busLine.getEndStation() + ",";
			sql += "startTime='" + busLine.getStartTime() + "',";
			sql += "endTime='" + busLine.getEndTime() + "',";
			sql += "company='" + busLine.getCompany() + "',";
			sql += "tjzd='" + busLine.getTjzd() + "',";
			sql += "polylinePoints='" + busLine.getPolylinePoints() + "'";
			sql += " where lineId=" + busLine.getLineId();
			db.executeUpdate(sql);
			result = "公交线路更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "公交线路更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
