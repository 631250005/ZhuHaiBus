package com.scrat.zhuhaibus.module.bus.search;

import android.text.TextUtils;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.modle.route.TipsRes;
import com.scrat.zhuhaibus.framework.util.AbsMap;
import com.scrat.zhuhaibus.framework.util.L;

import java.util.Collections;
import java.util.Iterator;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchStopPresenter implements SearchStopContract.Presenter {
    private SearchStopContract.View view;
    private Subscription subscription;

    public SearchStopPresenter(SearchStopContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void search(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        view.showSearching();
        subscription = DataRepository.getInstance().getAmapService()
                .inputTips(
                        "rsv3",
                        "ceb54024fae4694f734b1006e8dc8324",
                        "0756",
                        false,
                        "JS",
                        "2.0",
                        "1.3",
                        key)
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<TipsRes>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showSearchError(R.string.error_msg);
                    }

                    @Override
                    public void onNext(TipsRes tipsRes) {
                        L.d("%s", tipsRes.getTips());
                        if (tipsRes.getTips() != null && !tipsRes.getTips().isEmpty()) {
                            for (Iterator<AbsMap<String, Object>> iterator = tipsRes.getTips().iterator(); iterator.hasNext();) {
                                AbsMap<String, Object> tip = iterator.next();
                                try {
                                    String location = (String) tip.get("location");
                                    if (TextUtils.isEmpty(location)) {
                                        iterator.remove();
                                    }
                                } catch (Exception e) {
                                    iterator.remove();
                                }
                            }
                            view.showSearchRes(tipsRes.getTips());
                        } else {
                            view.showSearchRes(Collections.emptyList());
                        }
                    }
                });

    }
}
