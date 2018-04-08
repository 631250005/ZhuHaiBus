package com.scrat.zhuhaibus.module.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.scrat.zhuhaibus.MainActivity;
import com.scrat.zhuhaibus.framework.common.BaseActivity;

/**
 * Created by scrat on 2018/3/26.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.show(this);
        finish();
    }
}
