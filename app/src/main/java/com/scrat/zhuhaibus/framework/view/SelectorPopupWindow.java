package com.scrat.zhuhaibus.framework.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scrat.zhuhaibus.R;
import com.scrat.zhuhaibus.databinding.PopupSelectorBinding;

import java.util.Map;

/**
 * Created by yixuanxuan on 2017/7/10.
 */

public class SelectorPopupWindow extends PopupWindow {

    private PopupSelectorBinding binding;
    private LayoutInflater inflater;

    public SelectorPopupWindow(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        binding = PopupSelectorBinding.inflate(inflater, null);
        setContentView(binding.getRoot());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);

        binding.content.setOnClickListener(v -> dismiss());
        setFocusable(true);
    }

    public SelectorPopupWindow refreshItems(Map<String, View.OnClickListener> itemMap) {
        binding.contentList.removeAllViews();
        View lastLineView = null;
        for (Map.Entry<String, View.OnClickListener> item : itemMap.entrySet()) {
            View v = inflater.inflate(
                    R.layout.item_list_popup_selector, binding.contentList, false);
            TextView textView = v.findViewById(R.id.title);
            textView.setText(item.getKey());
            textView.setOnClickListener(v1 -> {
                dismiss();
                item.getValue().onClick(v1);
            });
            lastLineView = v.findViewById(R.id.line);
            binding.contentList.addView(v);
        }
        if (lastLineView != null) {
            lastLineView.setVisibility(View.GONE);
        }
        return this;
    }

    public void show(View view) {
        showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
