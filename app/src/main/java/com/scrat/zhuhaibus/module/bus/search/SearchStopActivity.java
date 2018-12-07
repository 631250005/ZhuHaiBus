package com.scrat.zhuhaibus.module.bus.search;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.databinding.ActivitySearchStopBinding;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewAdapter;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewHolder;
import com.scrat.zhuhaibus.framework.util.AbsMap;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class SearchStopActivity extends BaseActivity implements SearchStopContract.View {
    private static final String NAME = "name";
    private static final String LOCATION = "location";

    public static void show(Activity activity, int requestCode, String hint) {
        Intent i = new Intent(activity, SearchStopActivity.class);
        i.putExtra(NAME, hint);
        activity.startActivityForResult(i, requestCode);
    }

    public static String parseLocation(Intent i) {
        return i.getStringExtra(LOCATION);
    }

    public static String parseName(Intent i) {
        return i.getStringExtra(NAME);
    }

    private ActivitySearchStopBinding binding;
    private SearchStopContract.Presenter presenter;
    private Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_stop);

        adapter = new Adapter((name, location) -> {
            Intent i = new Intent();
            i.putExtra(NAME, name);
            i.putExtra(LOCATION, location);
            setResult(RESULT_OK, i);
            finish();
        });
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setHasFixedSize(true);
        binding.list.setAdapter(adapter);
        new SearchStopPresenter(this);

        binding.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (start == 0 && after == 0) {
                    binding.clear.setVisibility(View.GONE);
                } else {
                    binding.clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }

                search(content);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String hint = getIntent().getStringExtra(NAME);
        if (!TextUtils.isEmpty(hint)) {
            binding.content.setText(hint);
            binding.content.selectAll();
            binding.clear.setVisibility(View.VISIBLE);
            search(hint);
        }

        binding.content.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(binding.content.getText().toString());
                return true;
            }
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SearchStopActivity");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SearchStopActivity");
    }

    private void search(String content) {
        presenter.search(content);
    }

    @Override
    public void setPresenter(SearchStopContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSearching() {

    }

    @Override
    public void showSearchError(int msg) {

    }

    @Override
    public void showSearchRes(List<AbsMap<String, Object>> tips) {
        adapter.replaceData(tips);
    }

    public void clear(View view) {
        binding.content.setText("");
        adapter.clearData();
    }

    private interface OnItemClickListener {
        void onItemClick(String name, String location);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<AbsMap<String, Object>> {
        private OnItemClickListener listener;

        private Adapter(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, AbsMap<String, Object> tip) {
            String stopName = (String) tip.get("name");
            String location = (String) tip.get("location");
            holder.setText(R.id.content, stopName)
                    .setOnClickListener(v -> listener.onItemClick(stopName, location));
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            return BaseRecyclerViewHolder.newInstance(parent, R.layout.item_lists_specs_single_line_with_icon);
        }
    }
}
