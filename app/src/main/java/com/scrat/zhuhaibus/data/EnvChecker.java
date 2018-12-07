package com.scrat.zhuhaibus.data;

import android.content.Context;

import com.scrat.zhuhaibus.data.local.Preferences;
import com.scrat.zhuhaibus.framework.util.L;
import com.scrat.zhuhaibus.framework.util.Utils;

public class EnvChecker {
    // call by app onCreate
    public static void refresh(Context applicationContext) {
        Preferences preferences = Preferences.getInstance();
        long currTs = System.currentTimeMillis();

        long times = preferences.getOpenAppTimes();
        preferences.setOpenAppTimes(times + 1L);

        long firstOpenAppTs = preferences.getFirstOpenAppTs();
        if (firstOpenAppTs == 0L) {
            preferences.setFirstOpenAppTs(currTs);
        }

        long lastOpenTs = preferences.getCurrOpenAppTs();
        preferences.setLastOpenAppTs(lastOpenTs);
        preferences.setCurrOpenAppTs(currTs);

        int currVerCode = Utils.getVersionCode(applicationContext);

        int firstOpenAppVerCode = preferences.getFirstOpenAppVerCode();
        if (firstOpenAppVerCode == 0) {
            preferences.setFirstOpenAppVerCode(currVerCode);
        }

        int lastVerCode = preferences.getCurrAppVerCode();
        if (lastVerCode != currVerCode) {
            preferences.setCurrAppVerCode(currVerCode);
            preferences.setLastAppVerCode(lastVerCode);
        }
    }

    public static boolean isNewVersionUser(Context applicationContext) {
        int appVerCode = Utils.getVersionCode(applicationContext);
        int firstOpenAppVerCode = Preferences.getInstance().getFirstOpenAppVerCode();
        return firstOpenAppVerCode == appVerCode;
    }

    public static boolean canShowAd() {
        long times = Preferences.getInstance().getOpenAppTimes();
        if (times < 20) { // 打开20次以上
            L.d("打开应用少于20次");
            return false;
        }

        long firstOpenAppTs = Preferences.getInstance().getFirstOpenAppTs();
        long nowTs = System.currentTimeMillis();
        if (nowTs - firstOpenAppTs < 259200000L) { // 安装应用3天
            L.d("安装应用少于3天");
            return false;
        }

        return true;
    }
}
