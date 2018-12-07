package com.scrat.zhuhaibus.module.pay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.scrat.zhuhaibus.App;
import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.EnvChecker;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.util.L;
import com.scrat.zhuhaibus.framework.view.SelectorPopupWindow;
import com.scrat.zhuhaibus.module.feedback.FeedbackActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.LinkedHashMap;
import java.util.Map;

public class PayActivity extends BaseActivity {
    public static void show(Context context) {
        Intent i = new Intent(context, PayActivity.class);
        context.startActivity(i);
    }

    private SelectorPopupWindow selector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        TextView title = findViewById(R.id.title);
        title.setText(getString(R.string.scan_pay));
        findViewById(R.id.more).setVisibility(View.VISIBLE);
        selector = new SelectorPopupWindow(this);

        initAd();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PayActivity");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PayActivity");
    }

    @Override
    protected void onDestroy() {
        if (selector != null && selector.isShowing()) {
            selector.dismiss();
        }
        super.onDestroy();
    }

    private void initAd() {
        if (!EnvChecker.canShowAd()) {
            return;
        }
        Context ctx = getApplicationContext();
        AdView adView = findViewById(R.id.ad_view);
        adView.post(() -> {
            try {
                MobileAds.initialize(ctx, App.AD_APP_ID);
                AdRequest adRequest = new AdRequest
                        .Builder()
                        .build();
                adView.loadAd(adRequest);
            } catch (Exception e) {
                L.e(e);
            }
        });
    }

    public void alipayBusCode(View view) {
        boolean openSuccess = openApp("alipayqr://platformapi/startapp?saId=200011235");
        if (!openSuccess) {
            openSuccess = openApp("alipays://platformapi/startapp?appId=20000056");
        }
        if (!openSuccess) {
            showMessage(R.string.scan_pay_open_fail);
        }
    }

    public void wechatBusCode(View view) {
        boolean openSuccess = openApp("weixin://");
        if (!openSuccess) {
            showMessage(R.string.scan_pay_open_fail);
        }
    }

    public void more(View view) {
        Map<String, View.OnClickListener> items = new LinkedHashMap<>();
        items.put(getString(R.string.feedback), v -> FeedbackActivity.show(this));

        selector.refreshItems(items).show(view);
    }

    public boolean openApp(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(i);
            return true;
        } catch (Exception e) {
            L.e(e);
            return false;
        }
    }
}
