package com.scrat.zhuhaibus;

import android.app.Application;

import com.scrat.zhuhaibus.data.local.Preferences;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by scrat on 2018/3/25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            MobclickAgent.setDebugMode(true);
        }
        Preferences.getInstance().init(getApplicationContext());
    }
}
