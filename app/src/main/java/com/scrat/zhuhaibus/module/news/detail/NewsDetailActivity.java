package com.scrat.zhuhaibus.module.news.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.data.modle.News;
import com.scrat.zhuhaibus.databinding.ActivityNewsDetailBinding;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.common.ViewAnnotation;
import com.scrat.zhuhaibus.framework.glide.GlideApp;
import com.scrat.zhuhaibus.framework.glide.GlideRequests;

@ViewAnnotation(modelName = "news_detail")
public class NewsDetailActivity extends BaseActivity implements NewsDetailContract.View {
    private static final String ID = "id";
    public static void show(Context ctx, String newsId) {
        Intent i = new Intent(ctx, NewsDetailActivity.class);
        i.putExtra(ID, newsId);
        ctx.startActivity(i);
    }

    private NewsDetailContract.Presenter presenter;
    private ActivityNewsDetailBinding binding;
    private GlideRequests glideRequests;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glideRequests = GlideApp.with(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
        String newsId = getIntent().getStringExtra(ID);
        new NewsDetailPresenter(this, newsId);
        presenter.loadData();
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingData() {

    }

    @Override
    public void showLoadDataFail(int resId) {
        showMessage(resId);
    }

    @Override
    public void showData(News news) {
        binding.title.setText(news.getTitle());
        binding.newsTitle.setText(news.getTitle());
        glideRequests.load(news.getCover()).fitCenter().into(binding.newsCover);
        binding.newsDetail.fromHtml(news.getDetail());
        if (!TextUtils.isEmpty(news.getPt())) {
            binding.from.setText(news.getPt());
        }
    }
}
