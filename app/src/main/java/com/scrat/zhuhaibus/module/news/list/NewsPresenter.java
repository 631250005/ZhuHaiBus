package com.scrat.zhuhaibus.module.news.list;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.modle.News;
import com.scrat.zhuhaibus.data.modle.Res;
import com.scrat.zhuhaibus.data.modle.ResList;
import com.scrat.zhuhaibus.framework.util.L;

import java.util.Collections;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsPresenter implements NewsContract.Presenter {
    private NewsContract.View view;
    private String index;
    private static final int SIZE = 20;
    private Subscription subscription;

    public NewsPresenter(NewsContract.View view) {
        index = "0";
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadData(boolean refresh) {
        if (refresh) {
            index = "0";
        }

        if ("-1".equals(index)) {
            return;
        }

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        view.showNewsLoading();
        subscription = DataRepository.getInstance().getCoreService()
                .getNewsList(index, SIZE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ResList<News>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("index=%s size=%s", index, SIZE);
                        e.printStackTrace();
                        view.showNewsLoadError(R.string.error_msg);
                    }

                    @Override
                    public void onNext(Res<ResList<News>> resListRes) {
                        index = resListRes.getData().getIndex();
                        if (resListRes.getData() == null || resListRes.getData().getItems() == null) {
                            view.showNews(Collections.emptyList(), !refresh);
                            return;
                        }
                        view.showNews(resListRes.getData().getItems(), !refresh);
                    }
                });
    }
}
