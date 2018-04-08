package com.scrat.zhuhaibus.module.news.list;

import com.scrat.zhuhaibus.data.modle.News;

import java.util.List;

public interface NewsContract {
    interface Presenter {
        void loadData(boolean refresh);
    }

    interface View {
        void setPresenter(Presenter presenter);

        void showNewsLoading();

        void showNews(List<News> list, boolean append);

        void showNewsLoadError(int resId);
    }
}
