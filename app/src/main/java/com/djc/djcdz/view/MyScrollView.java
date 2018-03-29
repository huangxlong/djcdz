package com.djc.djcdz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * 解决ScrollView和RecyclerView滑动冲突
 * Created by Administrator
 * on 2018/3/21 星期三.
 */

public class MyScrollView extends ScrollView {

    private int downX;

    private int downY;

    private int mTouchSlop;

    public MyScrollView(Context context) {

        super(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }


    public MyScrollView(Context context, AttributeSet attrs) {

        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }


    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }


    @Override

    public boolean onInterceptTouchEvent(MotionEvent e) {

        int action = e.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                downX = (int) e.getRawX();

                downY = (int) e.getRawY();

            case MotionEvent.ACTION_MOVE:

                int moveY = (int) e.getRawY();

                if (Math.abs(moveY - downY) > mTouchSlop) {

                    return true;

                }

        }

        return super.onInterceptTouchEvent(e);

    }

}
