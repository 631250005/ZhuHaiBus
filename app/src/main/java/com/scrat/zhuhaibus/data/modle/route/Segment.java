package com.scrat.zhuhaibus.data.modle.route;

import java.io.Serializable;

public class Segment implements Serializable {
    // Fuck Amap(高德地图)
    // walking = {} walking = []
//    private AbsMap walking;
    private Bus bus;

    public Bus getBus() {
        return bus;
    }

//    public AbsMap getWalking() {
//        return walking;
//    }

}
