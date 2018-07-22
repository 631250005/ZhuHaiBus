package com.scrat.zhuhaibus.data.modle.route;

import java.io.Serializable;
import java.util.List;

public class Route implements Serializable {
//    private String origin;
//    private String destination;
    private String distance;
//    private String taxi_cost;
    private List<Transit> transits;

    public String getDistance() {
        return distance;
    }

    public List<Transit> getTransits() {
        return transits;
    }
}
