package com.scrat.zhuhaibus.data.modle;

import java.io.Serializable;

/**
 * Created by scrat on 2018/3/24.
 */

public class BaseXinHeRes<T> implements Serializable {
    private int flag;
    private T data;

    public int getFlag() {
        return flag;
    }

    public T getData() {
        return data;
    }
}
