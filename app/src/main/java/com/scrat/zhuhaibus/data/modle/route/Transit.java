package com.scrat.zhuhaibus.data.modle.route;

import java.io.Serializable;
import java.util.List;

public class Transit implements Serializable {
    private String cost;
    private int duration;
//    private String nightflag;
    private int walking_distance;
    private String distance;
//    private String missed;
    private List<Segment> segments;

    public String getCost() {
        return cost;
    }

    public int getDuration() {
        return duration;
    }

    public int getWalking_distance() {
        return walking_distance;
    }

    public String getDistance() {
        return distance;
    }

    public List<Segment> getSegments() {
        return segments;
    }
}
