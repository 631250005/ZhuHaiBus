package com.scrat.zhuhaibus.framework.db;

import android.content.Context;

import com.scrat.zhuhaibus.data.dao.BusLineDao;
import com.scrat.zhuhaibus.data.dao.BusStopDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixuanxuan on 2017/1/28.
 */

public class AppDatabaseConfig implements DatabaseConfig {
    private static final String DB_NAME = "bus.db";
    private static final int DB_VER = 1;

    private static class SingletonHolder {
        private static AppDatabaseConfig instance = new AppDatabaseConfig();
    }

    public static AppDatabaseConfig getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    public int getDatabaseVersion() {
        return DB_VER;
    }

    @Override
    public List<Class<? extends SQLiteManager.SQLiteTable>> getTables(Context context) {
        List<Class<? extends SQLiteManager.SQLiteTable>> ret = new ArrayList<>();
        ret.add(BusLineDao.class);
        ret.add(BusStopDao.class);
        return ret;
    }
}
