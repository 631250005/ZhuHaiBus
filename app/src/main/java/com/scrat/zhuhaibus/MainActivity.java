package com.scrat.zhuhaibus;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.scrat.zhuhaibus.databinding.ActivityMainBinding;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.common.ViewAnnotation;
import com.scrat.zhuhaibus.module.bus.list.BusListFragment;
import com.scrat.zhuhaibus.module.news.list.NewsFragment;
import com.scrat.zhuhaibus.module.setting.SettingFragment;
import com.scrat.zhuhaibus.module.update.UpdateHelper;

@ViewAnnotation(modelName = "main")
public class MainActivity extends BaseActivity {

    public static void show(Context ctx) {
        Intent i = new Intent(ctx, MainActivity.class);
        ctx.startActivity(i);
    }

    private BusListFragment busListFragment;
    private SettingFragment settingFragment;
    private NewsFragment newsFragment;
    private ActivityMainBinding binding;
    private UpdateHelper updateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initFragment();
        switchFragment(R.id.content, busListFragment);

        binding.navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(R.id.content, busListFragment);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(R.id.content, newsFragment);
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(R.id.content, settingFragment);
                    return true;
            }
            return false;
        });

        updateHelper = new UpdateHelper();
        updateHelper.init(this)
                .checkUpdate(this, false, false);
    }

    @Override
    protected void onDestroy() {
        updateHelper.release();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode >= 10 && requestCode <= 19) {
            busListFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void switchFragment(int containerViewId, Fragment target) {
        super.switchFragment(containerViewId, target);
        if (target == busListFragment) {
            busListFragment.refreshHistory();
        }
    }

    @Override
    public void onBackPressed() {
        if (!isFragmentActive(busListFragment)) {
            binding.navigation.setSelectedItemId(R.id.navigation_home);
            switchFragment(R.id.content, busListFragment);
            return;
        }
        super.onBackPressed();
    }

    private void initFragment() {
        busListFragment = BusListFragment.newInstance();
        settingFragment = SettingFragment.newInstance();
        newsFragment = NewsFragment.newInstance();
    }
}
