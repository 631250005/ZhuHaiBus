package com.scrat.zhuhaibus.module.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.zhuhaibus.databinding.FragmentRouteBinding;
import com.scrat.zhuhaibus.framework.common.BaseFragment;
import com.scrat.zhuhaibus.framework.common.ViewAnnotation;

@ViewAnnotation(modelName = "RouteFragment")
public class RouteFragment extends BaseFragment {

    public static RouteFragment newInstance() {
        return new RouteFragment();
    }

    private FragmentRouteBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentRouteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
