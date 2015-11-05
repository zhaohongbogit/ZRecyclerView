package com.zhzephi.recycler;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.zhzephi.recycler.network.HttpRequestUtils;
import com.zhzephi.recycler.page.IPageList;
import com.zhzephi.recycler.page.Page;
import com.zhzephi.recycler.widget.ZListRecyclerView;
import com.zhzephi.recycler.widget.ZSwipeRefreshLayout;

/**
 * Created by ZHZEPHI on 2015/10/16.
 */
public class ZListPageView extends ZListRecyclerView implements IPageList {

    HttpRequestUtils httpRequestUtils;
    Page page = new Page();

    public ZListPageView(Context context) {
        super(context);
    }

    public ZListPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addListener();
    }

    public ZListPageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addListener();
    }

    public void setHttpRequestUtils(HttpRequestUtils httpRequestUtils) {
        this.httpRequestUtils = httpRequestUtils;
        addListener();
    }

    private void addListener() {
        addSwipeRefreshListener();
        addLoadNextListener();
    }

    private void addLoadNextListener() {
        if (onLoadListener == null) {
            addOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LayoutManager layoutManager = getLayoutManager();
                    int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                        showPageNext();
                    }
                }
            });
        }
    }

    private void addSwipeRefreshListener() {
        if (httpRequestUtils != null) {
            ZSwipeRefreshLayout swipeRefresh = httpRequestUtils.getSwipeRefreshLayout();
            if (swipeRefresh != null) {
                swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        showPageFirst();
                    }
                });
            }
        }
    }

    @Override
    public void showPageFirst() {
        if (httpRequestUtils != null) {
            httpRequestUtils.exec(page, page.getFirstPage());
        }
    }

    @Override
    public void showPageNext() {
        if (httpRequestUtils != null) {
            httpRequestUtils.exec(page, page.getNextPage());
        }
    }

}
