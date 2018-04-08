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
}
