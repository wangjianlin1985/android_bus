package com.chengxusheji.domain;

import java.sql.Timestamp;
public class StationToStation {
    /*��¼���*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*��ʼվ*/
    private BusStation startStation;
    public BusStation getStartStation() {
        return startStation;
    }
    public void setStartStation(BusStation startStation) {
        this.startStation = startStation;
    }

    /*�յ�վ*/
    private BusStation endStation;
    public BusStation getEndStation() {
        return endStation;
    }
    public void setEndStation(BusStation endStation) {
        this.endStation = endStation;
    }

}