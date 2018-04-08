package com.scrat.zhuhaibus.data.modle;

import java.io.Serializable;

/**
 * Created by scrat on 2018/3/24.
 */
public class BusLine implements Serializable {
    private String Id;
    private String Name;
    private String LineNumber;
    private int Direction;
    private String FromStation;
    private String ToStation;
    private String BeginTime;
    private String EndTime;
    private String Price;
    private String Interval;
    private String Description;
    private int StationCount;

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getLineNumber() {
        return LineNumber;
    }

    public int getDirection() {
        return Direction;
    }

    public String getFromStation() {
        return FromStation;
    }

    public String getToStation() {
        return ToStation;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getPrice() {
        return Price;
    }

    public String getInterval() {
        return Interval;
    }

    public String getDescription() {
        return Description;
    }

    public int getStationCount() {
        return StationCount;
    }

    public BusLine setId(String id) {
        Id = id;
        return this;
    }

    public BusLine setName(String name) {
        Name = name;
        return this;
    }

    public BusLine setFromStation(String fromStation) {
        FromStation = fromStation;
        return this;
    }

    public BusLine setToStation(String toStation) {
        ToStation = toStation;
        return this;
    }

    public BusLine setBeginTime(String beginTime) {
        BeginTime = beginTime;
        return this;
    }

    public BusLine setEndTime(String endTime) {
        EndTime = endTime;
        return this;
    }

    public BusLine setPrice(String price) {
        Price = price;
        return this;
    }

    public BusLine setStationCount(int stationCount) {
        StationCount = stationCount;
        return this;
    }
}
