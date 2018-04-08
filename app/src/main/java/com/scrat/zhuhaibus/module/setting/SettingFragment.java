package com.scrat.zhuhaibus.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.databinding.FragmentSettingBinding;
import com.scrat.zhuhaibus.framework.common.BaseFragment;
import com.scrat.zhuhaibus.framework.view.IosDialog;
import com.scrat.zhuhaibus.module.about.AboutActivity;
import com.scrat.zhuhaibus.module.feedback.FeedbackActivity;
import com.scrat.zhuhaibus.module.update.UpdateHelper;

/**
 * Created by scrat on 2018/3/26.
 */

public class SettingFragment extends BaseFragment implements SettingContract.View {
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    private FragmentSettingBinding binding;
    private IosDialog cacheDialog;
    private SettingContract.Presenter presenter;
    private UpdateHelper updateHelper;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        binding.setting.setOnClickListener(v -> updateHelper.checkUpdate(getActivity(), true, true));
        binding.about.setOnClickListener(v -> AboutActivity.show(getContext()));
        binding.feedback.setOnClickListener(v -> FeedbackActivity.show(getContext()));
        cacheDialog = new IosDialog(getContext())
                .setTitle(R.string.clear_cache)
                .setContent(R.string.clear_cache_content)
                .setNegative(R.string.cancel)
                .setPositive(R.string.delete, v -> {
                    presenter.clearCache();
                });
        new SettingPresenter(getApplicationContext(),this);
        binding.cleanCache.setOnClickListener(v -> {
            cacheDialog.show(binding.about);
        });

        updateHelper = new UpdateHelper();
        updateHelper.init(getContext());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        updateHelper.release();
        if (cacheDialog.isShowing()) {
            cacheDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showClearCacheSuccess() {
        showMsg(R.string.execute_success);
    }
}
