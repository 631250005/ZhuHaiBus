package com.scrat.zhuhaibus.module.bus.detail;

import android.content.Context;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.dao.BusStopDao;
import com.scrat.zhuhaibus.data.modle.BaseXinHeRes;
import com.scrat.zhuhaibus.data.modle.BusLine;
import com.scrat.zhuhaibus.data.modle.BusStation;
import com.scrat.zhuhaibus.data.modle.BusStop;
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

public class BusDetailPresenter implements BusDetailContract.BusDetailPresenter {
    private BusDetailContract.BusDetailView view;
    private BusLine line;
    private Subscription refreshSubscription;
    private BusStopDao busStopDao;

    public BusDetailPresenter(
            Context context,
            BusDetailContract.BusDetailView view,
            BusLine line) {
        busStopDao = new BusStopDao(context);
        this.line = line;
        this.view = view;
        view.setPresenter(this);
        view.showBusLine(line);
    }

    @Override
    public void initBusStop() {
        List<BusStop> stops = busStopDao.getBusStopList(line.getId());
        if (!stops.isEmpty()) {
            view.showBusStop(stops);
            refreshStation();
        } else {
            loadBusStop();
        }
    }

    @Override
    public void loadBusStop() {
        view.showLoadingBusStop();
        DataRepository.getInstance().getXinheApiService()
                .getBusStop("GetStationList", line.getId(), System.currentTimeMillis())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(busLines -> {
                    L.d("%s", busLines.getFlag());
                    if (busLines.getData() == null || busLines.getData().isEmpty()) {
                        return;
                    }
                    busStopDao.delete(line.getId());
                    busStopDao.save(busLines.getData(), line.getId());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseXinHeRes<List<BusStop>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showLoadBusStopError(R.string.error_msg);
                    }

                    @Override
                    public void onNext(BaseXinHeRes<List<BusStop>> busLines) {
                        L.d("%s", busLines.getFlag());
                        if (busLines.getData() == null) {
                            view.showLoadBusStopError(R.string.error_msg);
                        } else {
                            view.showBusStop(busLines.getData());
                        }
                        refreshStation();
                    }
                });
    }

    @Override
    public void refreshStation() {
        if (refreshSubscription != null && !refreshSubscription.isUnsubscribed()) {
            refreshSubscription.unsubscribe();
        }

        view.showRefreshStation();
        refreshSubscription = DataRepository.getInstance().getXinheApiService()
                .getBusStation("GetBusListOnRoad", line.getName(), line.getFromStation(), System.currentTimeMillis())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseXinHeRes<List<BusStation>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showRefreshStationError(R.string.error_msg);
                    }

                    @Override
                    public void onNext(BaseXinHeRes<List<BusStation>> busLines) {
                        L.d("%s", busLines.getFlag());
                        if (busLines.getData() == null) {
                            view.refreshStation(Collections.emptyList());
                        } else {
                            view.refreshStation(busLines.getData());
                        }
                    }
                });
    }

}
