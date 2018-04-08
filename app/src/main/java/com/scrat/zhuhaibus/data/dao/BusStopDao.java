package com.scrat.zhuhaibus.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.scrat.zhuhaibus.framework.db.AppDatabaseConfig;
import com.scrat.zhuhaibus.framework.db.BaseDao;
import com.scrat.zhuhaibus.data.modle.BusStop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by scrat on 2018/3/25.
 */

public class BusStopDao extends BaseDao<BusStop> {
    public BusStopDao(Context context) {
        super(BusLineEntry.TABLE_NAME, context.getApplicationContext(), AppDatabaseConfig.getInstance());
    }

    @Override
    protected BusStop findByCursor(Cursor c, int i) {
        return new BusStop()
                .setName(getStringFromCursor(c, BusLineEntry.NAME))
                .setId(getStringFromCursor(c, BusLineEntry.LINE_ID));
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Map<String, String> cols = new HashMap<>();
        cols.put(BusLineEntry._ID, INTEGER_PRIMARY_KEY);
        cols.put(BusLineEntry.BUS_ID, TEXT);
        cols.put(BusLineEntry.LINE_ID, TEXT);
        cols.put(BusLineEntry.NAME, TEXT);
        cols.put(BusLineEntry.LINE_INDEX, INT);
        createTable(database, cols);
    }

    private ContentValues getInsertCountValues(BusStop stop, String busId, int index) {
        ContentValues values = new ContentValues();
        values.put(BusLineEntry.BUS_ID, busId);
        values.put(BusLineEntry.NAME, stop.getName());
        values.put(BusLineEntry.LINE_ID, stop.getId());
        values.put(BusLineEntry.LINE_INDEX, index);
        return values;
    }

    public void delete(String busId) {
        getDatabase().delete(getTableName(), BusLineEntry.BUS_ID + "=?", new String[]{busId});
    }

    public void save(List<BusStop> stopList, String busId) {
        for (int i = 0; i < stopList.size(); i++) {
            save(stopList.get(i), busId, i);
        }
    }

    public boolean save(BusStop stop, String busId, int index) {
        ContentValues values = getInsertCountValues(stop, busId, index);
        return save(values) > 0;
    }

    public List<BusStop> getBusStopList(String busId) {
        Cursor cursor = getDatabase().query(
                BusLineEntry.TABLE_NAME,
                ALL_COLS,
                BusLineEntry.BUS_ID + "=?",
                new String[]{busId},
                null,
                null,
                BusLineEntry.LINE_INDEX + " asc");
        return findListByCursor(cursor);
    }

    private static abstract class BusLineEntry implements BaseColumns {
        private static final String TABLE_NAME = "bus_stop";
        private static final String BUS_ID = "bus_id";
        private static final String LINE_ID = "line_id";
        private static final String NAME = "name";
        private static final String LINE_INDEX = "line_index";
    }
}
