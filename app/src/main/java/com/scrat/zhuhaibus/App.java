package com.scrat.zhuhaibus;

import android.app.Application;

import com.scrat.zhuhaibus.data.DataRepository;
import com.scrat.zhuhaibus.data.local.Preferences;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by scrat on 2018/3/25.
 */

public class App extends Application {
    //    应用 ID：ca-app-pub-7011153870356750~2650984540
    //    广告单元 ID：ca-app-pub-7011153870356750/5013138594
    public static final String AD_APP_ID = "ca-app-pub-7011153870356750~2650984540";

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
