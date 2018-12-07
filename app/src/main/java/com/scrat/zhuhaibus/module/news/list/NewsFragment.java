package com.scrat.zhuhaibus.module.news.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.modle.News;
import com.scrat.zhuhaibus.databinding.FragmentNewsBinding;
import com.scrat.zhuhaibus.framework.common.BaseFragment;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewAdapter;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewHolder;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewOnScrollListener;
import com.scrat.zhuhaibus.framework.glide.GlideApp;
import com.scrat.zhuhaibus.framework.glide.GlideRequests;
import com.scrat.zhuhaibus.framework.util.L;
import com.scrat.zhuhaibus.module.news.detail.NewsDetailActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * Created by scrat on 2018/3/26.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View {
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    private FragmentNewsBinding binding;
    private NewsContract.Presenter presenter;
    private Adapter adapter;
    private BaseRecyclerViewOnScrollListener loadMoreListener;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        GlideRequests glideRequests = GlideApp.with(this);
        adapter = new Adapter(glideRequests);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        binding.list.setHasFixedSize(true);
        binding.list.setAdapter(adapter);
        binding.srl.setOnRefreshListener(() -> presenter.loadData(true));
        loadMoreListener = new BaseRecyclerViewOnScrollListener(layoutManager, () -> presenter.loadData(false));
        binding.list.addOnScrollListener(loadMoreListener);
        new NewsPresenter(this);
        presenter.loadData(true);

        binding.title.setOnClickListener(v -> layoutManager.scrollToPosition(0));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onEvent(getContext(), "view", "NewsFragment");
        MobclickAgent.onPageStart("NewsFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("NewsFragment");
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showNewsLoading() {
        if (!binding.srl.isRefreshing()) {
            binding.srl.setRefreshing(true);
        }
        L.d("loading");
    }

    @Override
    public void showNews(List<News> list, boolean append) {
        loadMoreListener.setLoading(false);
        binding.srl.setRefreshing(false);
        if (append) {
            adapter.addList(list);
        } else {
            adapter.replaceData(list);
        }
    }

    @Override
    public void showNewsLoadError(int resId) {
        loadMoreListener.setLoading(false);
        binding.srl.setRefreshing(false);
        showMsg(resId);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<News> {
        private GlideRequests glideRequests;

        private Adapter(GlideRequests glideRequests) {
            this.glideRequests = glideRequests;
        }

        @Override
        protected void onBindItemViewHolder(BaseRecyclerViewHolder holder, int position, News news) {
            Context ctx = holder.getContext();
            holder.setText(R.id.title, news.getTitle())
                    .setOnClickListener(R.id.container, view -> NewsDetailActivity.show(ctx, news.getNewsId()));
            glideRequests.load(news.getCover())
                    .placeholder(R.color.bg)
                    .centerCrop()
                    .into(holder.getImageView(R.id.img));
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            return BaseRecyclerViewHolder.newInstance(parent, R.layout.item_list_news);
        }
    }
}
