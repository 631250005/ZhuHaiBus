package com.scrat.zhuhaibus.data.modle.route;

import java.io.Serializable;
import java.util.List;

public class Buslines implements Serializable {
    private Stop departure_stop;
    private Stop arrival_stop;
    private String name;
//    private String id;
//    private String type;
    private String distance;
    private String duration;
//    private String polyline;
//    private String via_num;
    private List<Stop> via_stops;

    public Stop getDeparture_stop() {
        return departure_stop;
    }

    public Stop getArrival_stop() {
        return arrival_stop;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public List<Stop> getVia_stops() {
        return via_stops;
    }
}
