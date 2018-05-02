package com.scrat.zhuhaibus.module.bus.search;

import android.content.Context;
import android.text.TextUtils;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.dao.BusHistoryDao;
import com.scrat.zhuhaibus.data.dao.BusLineDao;
import com.scrat.zhuhaibus.data.modle.BaseXinHeRes;
import com.scrat.zhuhaibus.data.modle.BusLine;
import com.scrat.zhuhaibus.framework.util.L;

import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by scrat on 2018/3/25.
 */

public class SearchPresenter implements SearchContract.SearchPresenter {
    private SearchContract.SearchView view;
    private Subscription subscription;
    private BusLineDao busLineDao;
    private BusHistoryDao busHistoryDao;

    public SearchPresenter(Context ctx, SearchContract.SearchView view) {
        busLineDao = new BusLineDao(ctx);
        busHistoryDao = new BusHistoryDao(ctx);
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
        subscription = DataRepository.getInstance().getXinheApiService()
                .getBusLine("GetLineListByLineName", key, System.currentTimeMillis())
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<BaseXinHeRes<List<BusLine>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showSearchError(R.string.error_msg);
                    }

                    @Override
                    public void onNext(BaseXinHeRes<List<BusLine>> busLines) {
                        L.d("%s", busLines.getFlag());
                        if (busLines.getData() != null) {
                            view.showSearchRes(busLines.getData());
                        } else {
                            view.showSearchRes(Collections.emptyList());
                        }
                    }
                });
    }

    @Override
    public void saveHistory(BusLine line) {
        busLineDao.delete(line.getId());
        busLineDao.save(line);
        busHistoryDao.addTimes(line.getId());
    }
}
