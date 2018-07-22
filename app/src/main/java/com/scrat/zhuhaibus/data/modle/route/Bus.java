package com.scrat.zhuhaibus.data.modle.route;

import java.io.Serializable;
import java.util.List;

public class Bus implements Serializable {
    private List<Buslines> buslines;

    public List<Buslines> getBuslines() {
        return buslines;
    }

    public String getFromStop() {
        if (buslines == null || buslines.isEmpty()) {
            return "";
        }

        return buslines.get(0).getDeparture_stop().getName();
    }

    public String getToStop() {
        if (buslines == null || buslines.isEmpty()) {
            return "";
        }

        return buslines.get(0).getArrival_stop().getName();
    }

    @Override
    public String toString() {
        return "Bus{" +
                "buslines=" + buslines +
                '}';
    }
}
