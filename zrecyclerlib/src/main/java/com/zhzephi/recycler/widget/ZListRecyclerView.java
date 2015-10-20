package com.zhzephi.recycler.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by ZHZEPHI on 2015/10/15.
 */
public class ZListRecyclerView extends ZRecyclerView {

    public ZListRecyclerView(Context context) {
        super(context);
        initListView();
    }

    public ZListRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListView();
    }

    public ZListRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initListView();
    }

    /**
     * 初始化列表组件，设置布局和增加Item空隙
     */
    private void initListView(){
        setLayoutManager();
        addItemDecoration();
    }

    public void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(layoutManager);
        super.setLayoutManager(layoutManager);
    }

    public void addItemDecoration() {
        ZDefaultDivider divider = new ZDefaultDivider();
        super.addItemDecoration(divider);
    }
}
