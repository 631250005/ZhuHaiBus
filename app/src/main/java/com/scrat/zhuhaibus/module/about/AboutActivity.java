package com.scrat.zhuhaibus.module.about;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.common.ViewAnnotation;
import com.scrat.zhuhaibus.framework.util.Utils;

/**
 * Created by scrat on 2018/3/26.
 */

@ViewAnnotation(modelName = "about")
public class AboutActivity extends BaseActivity {
    public static void show(Context context) {
        Intent i = new Intent(context, AboutActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_about);
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
