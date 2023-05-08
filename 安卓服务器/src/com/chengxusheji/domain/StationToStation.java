package com.chengxusheji.domain;

import java.sql.Timestamp;
public class StationToStation {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*起始站*/
    private BusStation startStation;
    public BusStation getStartStation() {
        return startStation;
    }
    public void setStartStation(BusStation startStation) {
        this.startStation = startStation;
    }

    /*终到站*/
    private BusStation endStation;
    public BusStation getEndStation() {
        return endStation;
    }
    public void setEndStation(BusStation endStation) {
        this.endStation = endStation;
    }

}