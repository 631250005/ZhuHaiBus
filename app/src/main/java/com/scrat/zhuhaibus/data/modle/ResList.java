package com.scrat.zhuhaibus.data.modle;

import java.util.List;

public class ResList<T> {
    private String index;
    private List<T> items;

    public String getIndex() {
        return index;
    }

    public List<T> getItems() {
        return items;
    }
}
