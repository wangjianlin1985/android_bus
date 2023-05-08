package com.mobileserver.domain;

public class BusLine {
    /*记录编号*/
    private int lineId;
    public int getLineId() {
        return lineId;
    }
    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    /*线路名称*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*起点站*/
    private int startStation;
    public int getStartStation() {
        return startStation;
    }
    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    /*终到站*/
    private int endStation;
    public int getEndStation() {
        return endStation;
    }
    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }

    /*首班车时间*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*末班车时间*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*所属公司*/
    private String company;
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    /*途径站点*/
    private String tjzd;
    public String getTjzd() {
        return tjzd;
    }
    public void setTjzd(String tjzd) {
        this.tjzd = tjzd;
    }

    /*地图线路坐标*/
    private String polylinePoints;
    public String getPolylinePoints() {
        return polylinePoints;
    }
    public void setPolylinePoints(String polylinePoints) {
        this.polylinePoints = polylinePoints;
    }

}