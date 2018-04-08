package com.scrat.zhuhaibus.module.bus.list;

import android.content.Context;

import com.scrat.zhuhaibus.data.dao.BusLineDao;
import com.scrat.zhuhaibus.data.modle.BusLine;

import java.util.List;

/**
 * Created by scrat on 2018/3/26.
 */

public class BusListPresenter implements BusListContract.Presenter {
    private BusListContract.View view;
    private BusLineDao busLineDao;

    public BusListPresenter(Context ctx, BusListContract.View view) {
        busLineDao = new BusLineDao(ctx);
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadHistory() {
        List<BusLine> lineList = busLineDao.findAll();
        if (lineList.isEmpty()) {
            view.showHistoryEmpty();
        } else {
            view.showHistory(lineList);
        }
    }

    @Override
    public void updateHistoryPos(String busId) {
        busLineDao.updateExecuteTime(busId);
    }

    @Override
    public void deleteHistory(String busId) {
        busLineDao.delete(busId);
        loadHistory();
    }
}
