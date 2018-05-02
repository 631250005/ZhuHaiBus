package com.scrat.zhuhaibus.module.bus.list;

import com.scrat.zhuhaibus.data.modle.BusLine;

import java.util.List;
import java.util.Map;

/**
 * Created by scrat on 2018/3/26.
 */

public interface BusListContract {

    interface Presenter {
        void loadHistory();

        void updateHistoryPos(String busId);

        void deleteHistory(String busId);
    }

    interface View {
        void setPresenter(Presenter presenter);

        void showHistoryEmpty();

        void showHistory(List<BusLine> list, Map<String, Integer> timesColorMap);
    }
}
