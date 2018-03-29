package com.djc.djcdz.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 横向居中的recycler
 * Created by Administrator
 * on 2018/3/26 星期一.
 */

public class HorCenterRecyclerView extends RecyclerView {
    public HorCenterRecyclerView(Context context) {
        super(context);
    }

    public HorCenterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorCenterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int lastWidth;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getChildCount() > 0) {
            int newWidth = 0;
            for (int i = 0; i < getChildCount(); i++) {
                newWidth += getChildAt(i).getMeasuredWidth();
            }
            if (lastWidth != newWidth) {
                lastWidth = newWidth;

                int empty = getMeasuredWidth() - newWidth;
                if (empty > 0) {
                    if (getPaddingLeft() == empty / 2) {
                        return;
                    }

                    setPadding(empty / 2, 0, empty / 2, 0);
                    //如果不再一次onLayout，子view就不会有padding
                    super.onLayout(changed, l, t, r, b);
                }
            }
        }
    }
}
