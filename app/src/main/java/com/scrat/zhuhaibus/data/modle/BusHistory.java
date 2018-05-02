package com.scrat.zhuhaibus.data.modle;

public class BusHistory {
    private String busId;
    private int times;

    public String getBusId() {
        return busId;
    }

    public BusHistory setBusId(String busId) {
        this.busId = busId;
        return this;
    }

    public int getTimes() {
        return times;
    }

    public BusHistory setTimes(int times) {
        this.times = times;
        return this;
    }
}
