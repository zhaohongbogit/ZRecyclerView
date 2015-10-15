package com.zongfi.myrecycleview.zWidget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by ZHZEPHI on 2015/10/15.
 */
public class ZSwipeRefreshLayout extends SwipeRefreshLayout {

    public ZSwipeRefreshLayout(Context context) {
        super(context);
        initStyle();
    }

    public ZSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyle();
    }

    public void initStyle() {
        setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_red_dark, android.R.color.holo_purple);
        setSize(SwipeRefreshLayout.DEFAULT);
        setProgressBackgroundColorSchemeResource(android.R.color.white);
        //这句话是为了，第一次进入页面的时候显示加载进度条
        setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }
}
