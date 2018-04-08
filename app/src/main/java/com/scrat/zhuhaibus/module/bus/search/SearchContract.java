package com.scrat.zhuhaibus.module.bus.search;

import com.scrat.zhuhaibus.data.modle.BusLine;

import java.util.List;

/**
 * Created by scrat on 2018/3/25.
 */

public interface SearchContract {
    interface SearchView {
        void setPresenter(SearchPresenter presenter);

        void showSearching();

        void showSearchError(int msgId);

        void showSearchRes(List<BusLine> lines);

    }

    interface SearchPresenter {
        void search(String key);

        void saveHistory(BusLine line);
    }
}
