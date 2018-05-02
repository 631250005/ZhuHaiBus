package com.scrat.zhuhaibus.data.dao.patch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.scrat.zhuhaibus.data.modle.BusHistory;
import com.scrat.zhuhaibus.framework.db.BaseDao;
import com.scrat.zhuhaibus.framework.db.IPatcher;

public class BusHistoryDaoPatch2 implements IPatcher<BusHistory> {
    public BusHistoryDaoPatch2() {
    }

    @Override
    public int getSupportMaxVersion() {
        return 2;
    }

    @Override
    public void execute(BaseDao<BusHistory> baseDAO, SQLiteDatabase database, Context context) {
        baseDAO.createTable(database);
    }
}