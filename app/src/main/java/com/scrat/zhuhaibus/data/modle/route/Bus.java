package com.scrat.zhuhaibus.data.modle.route;

import java.io.Serializable;
import java.util.List;

public class Bus implements Serializable {
    private List<Buslines> buslines;

    public List<Buslines> getBuslines() {
        return buslines;
    }
}
