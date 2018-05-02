package com.scrat.zhuhaibus.module.setting;

import android.content.Context;

import com.scrat.zhuhaibus.data.dao.BusHistoryDao;
import com.scrat.zhuhaibus.data.dao.BusLineDao;
import com.scrat.zhuhaibus.data.dao.BusStopDao;

public class SettingPresenter implements SettingContract.Presenter {
    private SettingContract.View view;
    private BusLineDao busLineDao;
    private BusStopDao busStopDao;
    private BusHistoryDao busHistoryDao;

    public SettingPresenter(Context ctx, SettingContract.View view) {
        busLineDao = new BusLineDao(ctx);
        busStopDao = new BusStopDao(ctx);
        busHistoryDao = new BusHistoryDao(ctx);
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void clearCache() {
        busStopDao.deleteAll();
        busLineDao.deleteAll();
        busHistoryDao.deleteAll();
        view.showClearCacheSuccess();
    }
}
