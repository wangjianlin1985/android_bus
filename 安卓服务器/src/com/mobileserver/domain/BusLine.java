package com.mobileserver.domain;

public class BusLine {
    /*��¼���*/
    private int lineId;
    public int getLineId() {
        return lineId;
    }
    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    /*��·����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*���վ*/
    private int startStation;
    public int getStartStation() {
        return startStation;
    }
    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    /*�յ�վ*/
    private int endStation;
    public int getEndStation() {
        return endStation;
    }
    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }

    /*�װ೵ʱ��*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*ĩ�೵ʱ��*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*������˾*/
    private String company;
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    /*;��վ��*/
    private String tjzd;
    public String getTjzd() {
        return tjzd;
    }
    public void setTjzd(String tjzd) {
        this.tjzd = tjzd;
    }

    /*��ͼ��·����*/
    private String polylinePoints;
    public String getPolylinePoints() {
        return polylinePoints;
    }
    public void setPolylinePoints(String polylinePoints) {
        this.polylinePoints = polylinePoints;
    }

}