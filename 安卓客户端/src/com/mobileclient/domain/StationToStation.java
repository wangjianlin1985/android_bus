package com.mobileclient.domain;

import java.io.Serializable;

public class StationToStation implements Serializable {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*起始站*/
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

}