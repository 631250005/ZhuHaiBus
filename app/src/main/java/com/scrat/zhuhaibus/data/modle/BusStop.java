package com.scrat.zhuhaibus.data.modle;

import java.io.Serializable;

/**
 * Created by scrat on 2018/3/24.
 */

public class BusStop  implements Serializable {
    private String Id;
    private String Name;
    private String Lng;
    private String Lat;
    private String Description;

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getLng() {
        return Lng;
    }

    public String getLat() {
        return Lat;
    }

    public String getDescription() {
        return Description;
    }

    public BusStop setId(String id) {
        Id = id;
        return this;
    }

    public BusStop setName(String name) {
        Name = name;
        return this;
    }
}
