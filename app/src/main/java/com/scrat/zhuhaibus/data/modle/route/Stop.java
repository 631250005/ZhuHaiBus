package com.scrat.zhuhaibus.data.modle.route;

import java.io.Serializable;

public class Stop implements Serializable {
    private String name;
//    private String id;
//    private String location;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "name='" + name + '\'' +
                '}';
    }
}
