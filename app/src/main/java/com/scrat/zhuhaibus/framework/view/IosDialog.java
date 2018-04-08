package com.scrat.zhuhaibus.framework.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.scrat.zhuhaibus.databinding.PopupIosDialogBinding;

/**
 * Created by scrat on 2017/7/25.
 */

public class IosDialog extends PopupWindow {

    private PopupIosDialogBinding binding;
    private String positive;
    private String negative;
    private String neutral;

    public IosDialog(Context context) {
        super(context);
        binding = PopupIosDialogBinding.inflate(LayoutInflater.from(context), null);
        setContentView(binding.getRoot());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);
        setOutsideTouchable(true);
        setFocusable(true);

        binding.container.setOnClickListener(v -> dismiss());
    }

    public IosDialog setTitle(int resId) {
        binding.title.setText(resId);
        return this;
    }

    public IosDialog setTitle(CharSequence title) {
        binding.title.setText(title);
        return this;
    }

    public IosDialog setContent(CharSequence content) {
        binding.content.setText(content);
        return this;
    }

    public IosDialog setContent(int resId) {
        binding.content.setText(resId);
        return this;
    }

    /**
     * 右
     */
    public IosDialog setPositive(@NonNull String positive) {
        return setPositive(positive, null);
    }

    /**
     * 右
     */
    public IosDialog setPositive(@NonNull String positive,
                                 @Nullable final View.OnClickListener listener) {
        binding.positive.setVisibility(View.VISIBLE);
        this.positive = positive;
        binding.positive.setText(positive);
        binding.positive.setOnClickListener(v -> {
            dismiss();
            if (listener == null) {
                return;
            }
            listener.onClick(v);
        });
        refreshLine();
        return this;
    }

    public IosDialog setPositive(int resId,
                                 @Nullable final View.OnClickListener listener) {
        return setPositive(getContentView().getContext().getString(resId), listener);
    }

    /**
     * 左
     */
    public IosDialog setNegative(@NonNull String negative) {
        return setNegative(negative, null);
    }

    public IosDialog setNegative(int resId) {
        return setNegative(getContentView().getContext().getString(resId), null);
    }

    /**
     * 左
     */
    public IosDialog setNegative(@NonNull String negative,
                                 @Nullable final View.OnClickListener listener) {
        binding.negative.setVisibility(View.VISIBLE);
        this.negative = negative;
        binding.negative.setText(negative);
        binding.negative.setOnClickListener(v -> {
            dismiss();
            if (listener == null) {
                return;
            }
            listener.onClick(v);
        });
        refreshLine();
        return this;
    }


    public IosDialog setNeutral(@NonNull String neutral) {
        return setNeutral(neutral, null);
    }

    /**
     * 中
     */
    public IosDialog setNeutral(@NonNull String neutral,
                                @Nullable final View.OnClickListener listener) {
        binding.neutral.setVisibility(View.VISIBLE);
        binding.neutral.setText(neutral);
        binding.neutral.setOnClickListener(v -> {
            dismiss();
            if (listener == null) {
                return;
            }
            listener.onClick(v);
        });
        this.neutral = neutral;
        refreshLine();
        return this;
    }

    private void refreshLine() {
        if (negative == null) {
            binding.line1.setVisibility(View.GONE);
        } else {
            binding.line1.setVisibility(View.VISIBLE);
        }

        if (neutral == null) {
            binding.line2.setVisibility(View.GONE);
        } else {
            binding.line2.setVisibility(View.VISIBLE);
        }

        if (positive == null) {
            if (neutral != null) {
                binding.line2.setVisibility(View.GONE);
            } else if (negative != null) {
                binding.line1.setVisibility(View.GONE);
            }
        }
    }

    public IosDialog show(View view) {
        showAtLocation(view, Gravity.CENTER, 0, 0);
        return this;
    }
}
