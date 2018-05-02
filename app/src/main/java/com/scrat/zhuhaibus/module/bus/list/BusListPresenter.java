package com.scrat.zhuhaibus.module.bus.list;

import android.content.Context;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.dao.BusHistoryDao;
import com.scrat.zhuhaibus.data.dao.BusLineDao;
import com.scrat.zhuhaibus.data.modle.BusHistory;
import com.scrat.zhuhaibus.data.modle.BusLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by scrat on 2018/3/26.
 */

public class BusListPresenter implements BusListContract.Presenter {
    private BusListContract.View view;
    private BusLineDao busLineDao;
    private BusHistoryDao busHistoryDao;
    private Integer[] colors;

    public BusListPresenter(Context ctx, BusListContract.View view) {
        busLineDao = new BusLineDao(ctx);
        busHistoryDao = new BusHistoryDao(ctx);
        this.view = view;
        colors = new Integer[] {
                android.R.color.white,
                R.color.success,
                R.color.warning,
                R.color.danger
        };
        view.setPresenter(this);
    }

    @Override
    public void loadHistory() {
        List<BusLine> lineList = busLineDao.findAll();
        if (lineList.isEmpty()) {
            view.showHistoryEmpty();
        } else {
            Map<String, Integer> timesColorMap = getTimesColorMap();
            view.showHistory(lineList, timesColorMap);
        }
    }

    private Map<String, Integer> getTimesColorMap() {
        List<BusHistory> histories = busHistoryDao.findAll();
        int maxTimes = getMaxTimes(histories);
        if (maxTimes < colors.length) {
            maxTimes = colors.length;
        }
        float area = (float) maxTimes / (float) colors.length;
        Map<String, Integer> busIdColorMap = new HashMap<>();
        for (BusHistory history : histories) {

            int colorPos = (int) Math.ceil(history.getTimes() / area);
            if (colorPos > colors.length) {
                colorPos = colors.length;
            }

            busIdColorMap.put(history.getBusId(), colors[colorPos - 1]);
        }
        return busIdColorMap;
    }

    private int getMaxTimes(List<BusHistory> histories) {
        int maxTimes = 0;
        for (BusHistory history : histories) {
            if (maxTimes == 0 || history.getTimes() > maxTimes) {
                maxTimes = history.getTimes();
            }
        }
        return maxTimes;
    }

    @Override
    public void updateHistoryPos(String busId) {
        busLineDao.updateExecuteTime(busId);
        busHistoryDao.addTimes(busId);
    }

    @Override
    public void deleteHistory(String busId) {
        busLineDao.delete(busId);
        loadHistory();
    }
}
