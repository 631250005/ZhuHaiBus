package com.scrat.zhuhaibus.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.scrat.zhuhaibus.data.dao.patch.BusHistoryDaoPatch2;
import com.scrat.zhuhaibus.data.modle.BusHistory;
import com.scrat.zhuhaibus.framework.db.AppDatabaseConfig;
import com.scrat.zhuhaibus.framework.db.BaseDao;

import java.util.HashMap;
import java.util.Map;

public class BusHistoryDao extends BaseDao<BusHistory> {

    public BusHistoryDao(Context context) {
        super(BusHistoryEntry.TABLE_NAME, context, AppDatabaseConfig.getInstance());
        registerPatcher(BusHistoryDaoPatch2.class);
    }

    @Override
    protected BusHistory findByCursor(Cursor c, int i) {
        return new BusHistory()
                .setBusId(getStringFromCursor(c, BusHistoryEntry.BUS_ID))
                .setTimes(getIntFromCursor(c, BusHistoryEntry.TIMES));
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Map<String, String> cols = new HashMap<>();
        cols.put(BusHistoryEntry._ID, INTEGER_PRIMARY_KEY);
        cols.put(BusHistoryEntry.BUS_ID, TEXT + " " + UNIQUE);
        cols.put(BusHistoryEntry.TIMES, INT);
        cols.put(BusHistoryEntry.UPDATE_AT, LONG);
        createTable(database, cols);
    }

    private void add(String busId) {
        ContentValues values = new ContentValues();
        values.put(BusHistoryEntry.BUS_ID, busId);
        values.put(BusHistoryEntry.TIMES, 1);
        values.put(BusHistoryEntry.UPDATE_AT, System.currentTimeMillis());
        save(values);
    }

    private BusHistory get(String busId) {
        return find(ALL_COLS, BusHistoryEntry.BUS_ID + "=?", new String[]{busId});
    }

    private void update(String busId, int times) {
        ContentValues values = new ContentValues();
        values.put(BusHistoryEntry.UPDATE_AT, System.currentTimeMillis());
        values.put(BusHistoryEntry.TIMES, times);

        String whereClause = BusHistoryEntry.BUS_ID + "=?";
        String[] whereArgs = new String[]{busId};

        getDatabase().update(BusHistoryEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public void addTimes(String busId) {
        BusHistory history = get(busId);
        if (history == null) {
            add(busId);
        } else {
            update(busId, history.getTimes() + 1);
        }
    }

    private static abstract class BusHistoryEntry implements BaseColumns {
        private static final String TABLE_NAME = "bus_history";
        private static final String BUS_ID = "bus_id";
        private static final String TIMES = "times";
        private static final String UPDATE_AT = "update_at";
    }

}
