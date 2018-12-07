package com.scrat.zhuhaibus.module.feedback;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.databinding.ActivityFeedbackBinding;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.view.IosDialog;
import com.umeng.analytics.MobclickAgent;

public class FeedbackActivity extends BaseActivity implements FeedbackContract.View {
    public static void show(Context ctx) {
        Intent i = new Intent(ctx, FeedbackActivity.class);
        ctx.startActivity(i);
    }

    private ActivityFeedbackBinding binding;
    private FeedbackContract.Presenter presenter;
    private IosDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        dialog = new IosDialog(this)
                .setTitle(R.string.feedback_success)
                .setContent(R.string.feedback_success_content)
                .setPositive(R.string.confirm, v -> finish());
        dialog.setOnDismissListener(this::finish);
        new FeedbackPresenter(this);
    }

    @Override
    protected void onDestroy() {
        if (dialog.isShowing()) {
            dialog.setOnDismissListener(null);
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onEvent(this, "view", "FeedbackActivity");
        MobclickAgent.onPageStart("FeedbackActivity");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FeedbackActivity");
    }

    @Override
    public void setPresenter(FeedbackContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onFeedback() {

    }

    @Override
    public void feedbackFail(int resId) {
        showMessage(resId);
    }

    @Override
    public void feedbackSuccess() {
        dialog.show(binding.content);
    }

    public void feedback(View view) {
        presenter.feedback("", binding.content.getText().toString());
    }
}
