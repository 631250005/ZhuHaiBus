package com.scrat.zhuhaibus.module.bus.search;

import com.scrat.zhuhaibus.framework.util.AbsMap;

import java.util.List;

public interface SearchStopContract {
    interface Presenter {
        void search(String key);
    }

    interface View {
        void setPresenter(Presenter presenter);

        void showSearching();

        void showSearchError(int msg);

        void showSearchRes(List<AbsMap<String, Object>> tips);
    }
}
