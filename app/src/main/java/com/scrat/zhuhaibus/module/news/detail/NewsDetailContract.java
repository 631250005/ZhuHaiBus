package com.scrat.zhuhaibus.module.news.detail;

import com.scrat.zhuhaibus.data.modle.News;

public interface NewsDetailContract {
    interface Presenter {
        void loadData();
    }

    interface View {
        void setPresenter(Presenter presenter);

        void showLoadingData();

        void showLoadDataFail(int resId);

        void showData(News news);
    }
}
