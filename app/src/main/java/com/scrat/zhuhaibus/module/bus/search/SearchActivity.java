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

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.databinding.ActivitySearchBusBinding;
import com.scrat.zhuhaibus.framework.common.BaseActivity;
import com.scrat.zhuhaibus.framework.common.BaseOnItemClickListener;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewAdapter;
import com.scrat.zhuhaibus.framework.common.BaseRecyclerViewHolder;
import com.scrat.zhuhaibus.framework.common.ViewAnnotation;
import com.scrat.zhuhaibus.data.modle.BusLine;

import java.util.List;

/**
 * Created by scrat on 2018/3/25.
 */

@ViewAnnotation(modelName = "search")
public class SearchActivity extends BaseActivity implements SearchContract.SearchView {
    private static final String DATA = "data";
    private ActivitySearchBusBinding binding;
    private SearchContract.SearchPresenter presenter;
    private Adapter adapter;

    public static void show(Activity activity, int requestCode) {
        Intent i = new Intent(activity, SearchActivity.class);
        activity.startActivityForResult(i, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_bus);

        new SearchPresenter(getApplicationContext(), this);
        adapter = new Adapter(busLine -> {
            presenter.saveHistory(busLine);
            Intent i = new Intent();
            i.putExtra(DATA, busLine);
            setResult(RESULT_OK, i);
            finish();
        });
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setHasFixedSize(true);
        binding.list.setAdapter(adapter);

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
    }

    public static BusLine parseData(Intent i) {
        return (BusLine) i.getSerializableExtra(DATA);
    }

    private void search(String content) {
        presenter.search(content);
    }

    @Override
    public void setPresenter(SearchContract.SearchPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSearching() {
        showTips(getString(R.string.searching));
    }

    @Override
    public void showSearchError(int msgId) {
        showMessage(msgId);
        binding.resultCount.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSearchRes(List<BusLine> lines) {
        adapter.replaceData(lines);
        String msgHint = getString(R.string.search_result_count);
        String msg = String.format(msgHint, lines.size());
        showTips(msg);
    }

    private void showTips(String msg) {
        binding.resultCount.setText(msg);
        binding.resultCount.setVisibility(View.VISIBLE);
    }

    public void clear(View view) {
        binding.content.setText("");
        binding.resultCount.setVisibility(View.INVISIBLE);
        adapter.clearData();
    }

    private static class Adapter extends BaseRecyclerViewAdapter<BusLine> {
        private BaseOnItemClickListener<BusLine> listener;

        private Adapter(BaseOnItemClickListener<BusLine> listener) {
            this.listener = listener;
        }

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, BusLine busLine) {
            String name = String.format(holder.getContext().getString(R.string.bus_line_to_2), busLine.getName(), busLine.getToStation());
            holder.setText(R.id.content, name)
                    .setOnClickListener(v -> {
                        listener.onItemClick(busLine);
                    });
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            return BaseRecyclerViewHolder.newInstance(parent, R.layout.item_lists_specs_single_line_with_icon);
        }
    }
}
