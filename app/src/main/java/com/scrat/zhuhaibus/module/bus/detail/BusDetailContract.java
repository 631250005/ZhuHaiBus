package com.scrat.zhuhaibus.module.bus.detail;

import com.scrat.zhuhaibus.data.modle.BusLine;
import com.scrat.zhuhaibus.data.modle.BusStation;
import com.scrat.zhuhaibus.data.modle.BusStop;

import java.util.List;

/**
 * Created by scrat on 2018/3/25.
 */

public interface BusDetailContract {
    interface BusDetailPresenter {
        void initBusStop();

        void loadBusStop();

        void refreshStation();
    }

    interface BusDetailView {

        void setPresenter(BusDetailPresenter presenter);

        void showBusLine(BusLine line);

        void showLoadingBusStop();

        void showLoadBusStopError(int resId);

        void showBusStop(List<BusStop> stopList);

        void showRefreshStation();

        void showRefreshStationError(int resId);

        void refreshStation(List<BusStation> stations);
    }
}
