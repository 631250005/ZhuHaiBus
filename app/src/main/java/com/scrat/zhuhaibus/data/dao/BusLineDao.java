package com.scrat.zhuhaibus.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.scrat.zhuhaibus.framework.db.AppDatabaseConfig;
import com.scrat.zhuhaibus.framework.db.BaseDao;
import com.scrat.zhuhaibus.data.modle.BusLine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by scrat on 2018/3/25.
 */

public class BusLineDao extends BaseDao<BusLine> {
    public BusLineDao(Context context) {
        super(BusLineEntry.TABLE_NAME, context.getApplicationContext(), AppDatabaseConfig.getInstance());
    }

    @Override
    protected BusLine findByCursor(Cursor c, int i) {
        return new BusLine()
                .setId(getStringFromCursor(c, BusLineEntry.BUS_ID))
                .setName(getStringFromCursor(c, BusLineEntry.NAME))
                .setFromStation(getStringFromCursor(c, BusLineEntry.FROM_STATION))
                .setToStation(getStringFromCursor(c, BusLineEntry.TO_STATION))
                .setBeginTime(getStringFromCursor(c, BusLineEntry.BEGIN_TIME))
                .setEndTime(getStringFromCursor(c, BusLineEntry.END_TIME))
                .setStationCount(getIntFromCursor(c, BusLineEntry.STATION_COUNT))
                .setPrice(getStringFromCursor(c, BusLineEntry.PRICE));
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Map<String, String> cols = new HashMap<>();
        cols.put(BusLineEntry._ID, INTEGER_PRIMARY_KEY);
        cols.put(BusLineEntry.BUS_ID, TEXT + " " + UNIQUE);
        cols.put(BusLineEntry.NAME, TEXT);
        cols.put(BusLineEntry.FROM_STATION, TEXT);
        cols.put(BusLineEntry.TO_STATION, TEXT);
        cols.put(BusLineEntry.BEGIN_TIME, TEXT);
        cols.put(BusLineEntry.END_TIME, TEXT);
        cols.put(BusLineEntry.STATION_COUNT, INT);
        cols.put(BusLineEntry.PRICE, TEXT);
        cols.put(BusLineEntry.UPDATE_AT, LONG);
        createTable(database, cols);
    }

    private ContentValues getInsertCountValues(BusLine line) {
        ContentValues values = new ContentValues();
        values.put(BusLineEntry.BUS_ID, line.getId());
        values.put(BusLineEntry.NAME, line.getName());
        values.put(BusLineEntry.FROM_STATION, line.getFromStation());
        values.put(BusLineEntry.TO_STATION, line.getToStation());
        values.put(BusLineEntry.BEGIN_TIME, line.getBeginTime());
        values.put(BusLineEntry.END_TIME, line.getEndTime());
        values.put(BusLineEntry.STATION_COUNT, line.getStationCount());
        values.put(BusLineEntry.PRICE, line.getPrice());
        long nowTs = System.currentTimeMillis();
        values.put(BusLineEntry.UPDATE_AT, nowTs);
        return values;
    }

    public void delete(String busId) {
        getDatabase().delete(getTableName(), BusLineEntry.BUS_ID + "=?", new String[]{busId});
    }

    public boolean save(BusLine line) {
        ContentValues values = getInsertCountValues(line);
        return save(values) > 0;
    }

    public void updateExecuteTime(String busId) {
        ContentValues values = new ContentValues();
        values.put(BusLineEntry.UPDATE_AT, new Date().getTime());
        getDatabase().update(getTableName(), values, BusLineEntry.BUS_ID + "=?", new String[]{busId});
    }

    @Override
    public List<BusLine> findAll() {
        return findAll(getDatabase(), BusLineEntry.UPDATE_AT + " " + DESC);
    }

    private static abstract class BusLineEntry implements BaseColumns {
        private static final String TABLE_NAME = "bus_line";
        private static final String BUS_ID = "bus_id";
        private static final String NAME = "name";
        private static final String FROM_STATION = "from_station";
        private static final String TO_STATION = "to_station";
        private static final String BEGIN_TIME = "begin_time";
        private static final String END_TIME = "end_time";
        private static final String STATION_COUNT = "station_count";
        private static final String PRICE = "price";
        private static final String UPDATE_AT = "update_at";
    }
}
