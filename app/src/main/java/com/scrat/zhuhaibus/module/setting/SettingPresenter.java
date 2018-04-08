package com.scrat.zhuhaibus.module.setting;

import android.content.Context;

import com.scrat.zhuhaibus.data.dao.BusLineDao;
import com.scrat.zhuhaibus.data.dao.BusStopDao;

public class SettingPresenter implements SettingContract.Presenter {
    private SettingContract.View view;
    private BusLineDao busLineDao;
    private BusStopDao busStopDao;

    public SettingPresenter(Context ctx, SettingContract.View view) {
        busLineDao = new BusLineDao(ctx);
        busStopDao = new BusStopDao(ctx);
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void clearCache() {
        busStopDao.deleteAll();
        busLineDao.deleteAll();
        view.showClearCacheSuccess();
    }
}
