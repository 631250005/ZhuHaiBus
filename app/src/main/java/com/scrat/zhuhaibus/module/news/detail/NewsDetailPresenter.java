package com.scrat.zhuhaibus.module.news.detail;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.modle.News;
import com.scrat.zhuhaibus.data.modle.Res;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View view;
    private String newsId;

    public NewsDetailPresenter(NewsDetailContract.View view, String newsId) {
        this.newsId = newsId;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadData() {
        view.showLoadingData();
        DataRepository.getInstance().getCoreService()
                .getNewsDetail(newsId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<News>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showLoadDataFail(R.string.error_msg);
                    }

                    @Override
                    public void onNext(Res<News> newsRes) {
                        view.showData(newsRes.getData());
                    }
                });
    }
}
