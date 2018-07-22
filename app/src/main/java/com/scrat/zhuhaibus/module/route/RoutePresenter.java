package com.scrat.zhuhaibus.module.route;

import android.text.TextUtils;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.modle.route.RouteRes;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RoutePresenter implements RouteContract.Presenter {
    private RouteContract.View view;
    private String startName;
    private String startLocation;
    private String endName;
    private String endLocation;

    public RoutePresenter(RouteContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void changeSide() {
        String tmpName = startName;
        String tmpLocation = startLocation;
        startName = endName;
        startLocation = endLocation;
        endName = tmpName;
        endLocation = tmpLocation;
        refreshList();
    }

    @Override
    public void search() {
        refreshList();
    }

    @Override
    public void setStart(String name, String location) {
        startName = name;
        startLocation = location;
        refreshList();
    }

    @Override
    public void setEnd(String name, String location) {
        endName = name;
        endLocation = location;
        refreshList();
    }

    private void refreshList() {
        view.showStop(startName, endName);

        if (TextUtils.isEmpty(startLocation)) {
            return;
        }

        if (TextUtils.isEmpty(endLocation)) {
            return;
        }
        view.showSearching();
        DataRepository.getInstance().getAmapService()
                .integrated(
                        startLocation,
                        endLocation,
                        "珠海",
                        0,
                        0,
                        "rsv3",
                        "珠海",
                        "ceb54024fae4694f734b1006e8dc8324",
                        "JS",
                        "2.0",
                        "1.3"
                ).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(routeRes -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RouteRes>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showSearchError(R.string.error_msg);
                    }

                    @Override
                    public void onNext(RouteRes routeRes) {
                        if (routeRes.getRoute() == null) {
                            // TODO
                            return;
                        }
                        view.showSearchRes(routeRes.getRoute().getTransits());
                    }
                });
    }
}
