package com.scrat.zhuhaibus.module.about;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.databinding.ActivityAboutBinding;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by scrat on 2018/3/26.
 */

public class AboutActivity extends BaseActivity {
    public static void show(Context context) {
        Intent i = new Intent(context, AboutActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        String ver = Utils.getVersionName(this) + "-" + Utils.getChannelName(this);
        binding.ver.setText(ver);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AboutActivity");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AboutActivity");
    }

    public void sendEmail(View view) {
        try {
            Intent data = new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse("mailto:huzhenjie.dev@gmail.com"));
            StringBuilder subject = new StringBuilder()
                    .append('[').append(getString(R.string.app_name)).append(']')
                    .append('[').append(Utils.getVersionName(this)).append(']')
                    .append('[').append(Utils.getVersionCode(this)).append(']')
                    .append('[').append(Utils.getChannelName(this)).append(']');
            data.putExtra(Intent.EXTRA_SUBJECT, subject.toString());
            data.putExtra(Intent.EXTRA_TEXT, getString(R.string.feedback_hint));
            startActivity(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
