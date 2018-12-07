package com.scrat.zhuhaibus.module.route;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.modle.route.Buslines;
import com.scrat.zhuhaibus.data.modle.route.Segment;
import com.scrat.zhuhaibus.data.modle.route.Transit;
import com.scrat.zhuhaibus.databinding.FragmentRouteBinding;
import com.scrat.zhuhaibus.databinding.ItemListBusStopTipBinding;
import com.scrat.zhuhaibus.databinding.ItemListRouteBusBinding;
import com.scrat.zhuhaibus.framework.common.BaseFragment;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewAdapter;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewHolder;
import com.scrat.zhuhaibus.framework.common.ViewAnnotation;
import com.scrat.zhuhaibus.framework.util.Utils;
import com.scrat.zhuhaibus.module.bus.search.SearchStopActivity;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import static android.app.Activity.RESULT_OK;

@ViewAnnotation(modelName = "RouteFragment")
public class RouteFragment extends BaseFragment implements RouteContract.View {

    private static final int REQUEST_CODE_EDIT_START = 20;
    private static final int REQUEST_CODE_EDIT_END = 21;

    public static RouteFragment newInstance() {
        return new RouteFragment();
    }

    private FragmentRouteBinding binding;
    private RouteContract.Presenter presenter;
    private Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentRouteBinding.inflate(inflater, container, false);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter();
        binding.list.setAdapter(adapter);
        binding.start.setOnClickListener(v -> {
            String start = binding.start.getText().toString();
            SearchStopActivity.show(getActivity(), REQUEST_CODE_EDIT_START, start);
        });
        binding.end.setOnClickListener(v -> {
            String end = binding.end.getText().toString();
            SearchStopActivity.show(getActivity(), REQUEST_CODE_EDIT_END, end);
        });
        binding.search.setOnClickListener(v -> {
            presenter.search();
        });
        binding.order.setOnClickListener(v -> {
            presenter.changeSide();
            showMsg(R.string.reverse_success);
        });
        new RoutePresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        String name;
        String location;
        switch (requestCode) {
            case REQUEST_CODE_EDIT_START:
                name = SearchStopActivity.parseName(data);
                location = SearchStopActivity.parseLocation(data);
                getPresenter().setStart(name, location);
                break;
            case REQUEST_CODE_EDIT_END:
                name = SearchStopActivity.parseName(data);
                location = SearchStopActivity.parseLocation(data);
                getPresenter().setEnd(name, location);
                break;
            default:
                break;
        }
    }

    public RouteContract.Presenter getPresenter() {
        // attempt to fix crash
        if (presenter == null) {
            new RoutePresenter(this);
        }
        return presenter;
    }

    @Override
    public void setPresenter(RouteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSearching() {

    }

    @Override
    public void showSearchError(int resId) {
        showMsg(resId);
    }

    @Override
    public void showSearchRes(List<Transit> list) {
        adapter.replaceData(list);
    }

    @Override
    public void showStop(String start, String end) {
        binding.start.setText(start);
        binding.end.setText(end);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<Transit> {

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, Transit transit) {
            Context ctx = holder.getRootView().getContext();
            String distance = Utils.formatDistance(ctx, transit.getWalking_distance());
            String subTitle = ctx.getString(R.string.walking) + distance;
            holder.setText(R.id.title, Utils.formatDuration(ctx, transit.getDuration()))
                    .setText(R.id.sub_title, subTitle);
            FlowLayout flowLayout = holder.getView(R.id.route);
            flowLayout.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(ctx);
            List<Segment> segments = transit.getSegments();
            ItemListRouteBusBinding lastBusBinding = null;
            for (Segment segment : segments) {
                ItemListRouteBusBinding busBinding = ItemListRouteBusBinding.inflate(inflater);
                lastBusBinding = busBinding;
                busBinding.busList.removeAllViews();
                List<Buslines> buses = segment.getBus().getBuslines();
                for (Buslines line : buses) {
                    String name = line.getSimpleName();
                    ItemListBusStopTipBinding tipBinding = ItemListBusStopTipBinding.inflate(inflater);
                    tipBinding.text.setText(name);
                    busBinding.busList.addView(tipBinding.getRoot());
                }
                if (buses.isEmpty()) {
                    busBinding.sign.setVisibility(View.VISIBLE);
                } else {
                    busBinding.sign.setVisibility(View.GONE);
                }
                flowLayout.addView(busBinding.getRoot());
            }
            if (lastBusBinding != null) {
                lastBusBinding.next.setVisibility(View.GONE);
            }
            holder.setOnClickListener(v -> {
                RouteDetailActivity.show(v.getContext(), transit);
            });
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            return BaseRecyclerViewHolder.newInstance(parent, R.layout.item_list_route);
        }
    }
}
