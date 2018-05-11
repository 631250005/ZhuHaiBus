package com.scrat.zhuhaibus.data.local;

import android.content.Context;

import com.scrat.zhuhaibus.framework.common.BaseSharedPreferences;

public class Preferences extends BaseSharedPreferences {
    private static final String FILE_NAME = "conf";

    private static class SingletonHolder {
        private static Preferences instance = new Preferences();
    }

    public static Preferences getInstance() {
        return SingletonHolder.instance;
    }

    public void init(Context applicationContext) {
        init(applicationContext, FILE_NAME);
    }

    private static final String SWITCH_AUTO_REFRESH = "switch_auto_refresh";
    public boolean isAutoRefresh() {
        return getBoolean(SWITCH_AUTO_REFRESH, true);
    }
    public void setAutoRefresh(boolean autoRefresh) {
        setBoolean(SWITCH_AUTO_REFRESH, autoRefresh);
    }

    private static final String LAST_SERVER_VER_CODE = "LAST_SERVER_VER_CODE";
    public int getLastServerVerCode() {
        return getInt(LAST_SERVER_VER_CODE, 0);
    }
    public void setLastServerVerCode(int code) {
        setInt(LAST_SERVER_VER_CODE, code);
    }

    private static final String LAST_SERVER_FORCE_VER_CODE = "last_server_force_ver_code";
    public int getLastServerForceVerCode() {
        return getInt(LAST_SERVER_FORCE_VER_CODE, 0);
    }
    public void setLastServerForceVerCode(int code) {
        setInt(LAST_SERVER_FORCE_VER_CODE, code);
    }

    private static final String LAST_IGNORE_VER_CODE = "last_ignore_ver_code";
    public int getLastIgnoreVerCode() {
        return getInt(LAST_IGNORE_VER_CODE, 0);
    }
    public void setLastIgnoreVerCode(int code) {
        setInt(LAST_IGNORE_VER_CODE, code);
    }

    private static final String FIRST_OPEN_APP_TS = "first_open_app_ts";
    public long getFirstOpenAppTs() {
        return getLong(FIRST_OPEN_APP_TS, 0L);
    }
    public void setFirstOpenAppTs(long ts) {
        setLong(FIRST_OPEN_APP_TS, ts);
    }

    private static final String LAST_OPEN_APP_TS = "last_open_app_ts";
    public long getLastOpenAppTs() {
        return getLong(LAST_OPEN_APP_TS, 0L);
    }
    public void setLastOpenAppTs(long ts) {
        setLong(LAST_OPEN_APP_TS, ts);
    }

    private static final String CURR_OPEN_APP_TS = "curr_open_app_ts";
    public long getCurrOpenAppTs() {
        return getLong(CURR_OPEN_APP_TS, 0L);
    }
    public void setCurrOpenAppTs(long ts) {
        setLong(CURR_OPEN_APP_TS, ts);
    }

    private static final String OPEN_APP_TIMES = "open_app_times";
    public long getOpenAppTimes() {
        return getLong(OPEN_APP_TIMES, 0L);
    }
    public void setOpenAppTimes(long times) {
        setLong(OPEN_APP_TIMES, times);
    }

    private static final String FIRST_OPEN_APP_VER_CODE = "first_open_app_ver_code";
    public int getFirstOpenAppVerCode() {
        return getInt(FIRST_OPEN_APP_VER_CODE, 0);
    }
    public void setFirstOpenAppVerCode(int code) {
        setInt(FIRST_OPEN_APP_VER_CODE, code);
    }

    private static final String LAST_APP_VER_CODE = "last_app_ver_code";
    public int getLastAppVerCode() {
        return getInt(LAST_APP_VER_CODE, 0);
    }
    public void setLastAppVerCode(int code) {
        setInt(LAST_APP_VER_CODE, code);
    }

    private static final String CURR_APP_VER_CODE = "curr_app_ver_code";
    public int getCurrAppVerCode() {
        return getInt(CURR_APP_VER_CODE, 0);
    }
    public void setCurrAppVerCode(int code) {
        setInt(CURR_APP_VER_CODE, code);
    }
}
