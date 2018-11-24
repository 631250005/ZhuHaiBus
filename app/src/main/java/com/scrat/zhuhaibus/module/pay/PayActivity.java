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
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.util.L;

public class PayActivity extends BaseActivity {
    public static void show(Context context) {
        Intent i = new Intent(context, PayActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        TextView title = findViewById(R.id.title);
        title.setText(getString(R.string.scan_pay));

        initAd();
    }

    private void initAd() {
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
        openApp("alipays://platformapi/startapp?appId=20000056");
    }

    public void wechatBusCode(View view) {
        openApp("weixin://");
    }

    public void openApp(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(i);
        } catch (Exception e) {
            L.e(e);
            showMessage(R.string.scan_pay_open_fail);
        }
    }
}
