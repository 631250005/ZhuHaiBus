package com.scrat.zhuhaibus.module.route;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.modle.route.Buslines;
import com.scrat.zhuhaibus.data.modle.route.Segment;
import com.scrat.zhuhaibus.data.modle.route.Transit;
import com.scrat.zhuhaibus.databinding.ActivityRouteDetailBinding;
import com.scrat.zhuhaibus.databinding.ItemListRouteBusItemBinding;
import com.scrat.zhuhaibus.databinding.ItemListRouteWalkItemBinding;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class RouteDetailActivity extends BaseActivity {
    private static final String DATA = "data";

    public static void show(Context ctx, Transit transit) {
        Intent i = new Intent(ctx, RouteDetailActivity.class);
        i.putExtra(DATA, transit);
        ctx.startActivity(i);
    }

    private ActivityRouteDetailBinding binding;
    private static int[] BG_LIST = new int[]{
            R.drawable.bg_meterial_blue,
            R.drawable.bg_meterial_yellow,
            R.drawable.bg_meterial_purple,
            R.drawable.bg_meterial_blue_light,
            R.drawable.bg_meterial_green
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_route_detail);
        Transit transit = (Transit) getIntent().getSerializableExtra(DATA);
        List<Segment> segments = transit.getSegments();
        LayoutInflater inflater = LayoutInflater.from(this);
        ItemListRouteWalkItemBinding startBinding = ItemListRouteWalkItemBinding.inflate(inflater, binding.list, false);
        startBinding.distance.setText(R.string.set_off);
        binding.list.addView(startBinding.getRoot());
        int index = 0;
        for (Segment segment : segments) {
            List<Buslines> buses = segment.getBus().getBuslines();
            if (buses == null || buses.isEmpty()) {
                continue;
            }
            String from = segment.getBus().getFromStop();
            String to = segment.getBus().getToStop();

            StringBuilder sb = new StringBuilder();
            for (Buslines line : buses) {
                sb.append(line.getSimpleName()).append(" 或 ");
            }
            String description;
            if (sb.length() > 0) {
                description = sb.substring(0, sb.length() - 3);
            } else {
                description = sb.toString();
            }

            ItemListRouteBusItemBinding itemBinding = ItemListRouteBusItemBinding.inflate(inflater, binding.list, false);
            itemBinding.stopContainer.setBackgroundResource(BG_LIST[index++]);
            itemBinding.stopName.setText(from);
            itemBinding.stopDescription.setText(description);
            itemBinding.targetStopName.setText("开往 " + to);
            binding.list.addView(itemBinding.getRoot());
        }

        ItemListRouteWalkItemBinding arrivalBinding = ItemListRouteWalkItemBinding.inflate(inflater, binding.list, false);
        arrivalBinding.distance.setText(R.string.arrivals);
        binding.list.addView(arrivalBinding.getRoot());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RouteDetailActivity");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RouteDetailActivity");
    }
}
