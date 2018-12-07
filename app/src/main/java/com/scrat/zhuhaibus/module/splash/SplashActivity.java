package com.scrat.zhuhaibus.module.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.scrat.zhuhaibus.MainActivity;
import com.scrat.zhuhaibus.data.EnvChecker;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by scrat on 2018/3/26.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EnvChecker.refresh(getApplicationContext());

        MainActivity.show(this);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onEvent(this, "view", "SplashActivity");
        MobclickAgent.onPageStart("SplashActivity");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SplashActivity");
    }
}
