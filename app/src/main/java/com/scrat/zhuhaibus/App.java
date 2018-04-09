package com.scrat.zhuhaibus;

import android.app.Application;

import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.local.Preferences;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by scrat on 2018/3/25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Preferences.getInstance().init(getApplicationContext());
        DataRepository.getInstance().init(getApplicationContext());

        if (BuildConfig.DEBUG) {
            MobclickAgent.setDebugMode(true);
        }
    }
}
