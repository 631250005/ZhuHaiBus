package com.scrat.zhuhaibus.data.modle;

import java.io.Serializable;

/**
 * Created by scrat on 2018/3/24.
 */

public class BusStation implements Serializable {
    private String BusNumber;
    private String CurrentStation;
    private String LastPosition;

    public String getBusNumber() {
        return BusNumber;
    }

    public String getCurrentStation() {
        return CurrentStation;
    }

    public String getLastPosition() {
        return LastPosition;
    }

    public boolean isArrival() {
        return "5".equals(LastPosition);
    }
}
