package com.scrat.zhuhaibus.data.modle.route;

import com.scrat.zhuhaibus.framework.util.AbsMap;

import java.util.List;

public class TipsRes {
    private String status;
    private int count;
    // Fuck Amap(高德地图)
    // different type of the res value in the same key
    // id=[] or id=""
    // location=[] or location=""
    // address=[] or address=""
    private List<AbsMap<String, Object>> tips;

    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }

    public List<AbsMap<String, Object>> getTips() {
        return tips;
    }
}
