package com.scrat.zhuhaibus.module.route;

import com.scrat.zhuhaibus.data.modle.route.Transit;

import java.util.List;

public interface RouteContract {
    interface View {
        void setPresenter(Presenter presenter);

        void showSearching();

        void showSearchError(int resId);

        void showSearchRes(List<Transit> list);

        void showStop(String start, String end);
    }

    interface Presenter {
        void changeSide();

        void search();

        void setStart(String name, String location);

        void setEnd(String name, String location);
    }
}
