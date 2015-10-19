package com.zongfi.zrecycleview.frame;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.zongfi.zrecycleview.frame.page.IPageList;
import com.zongfi.zrecycleview.frame.page.Page;

/**
 * Created by ZHZEPHI on 2015/10/16.
 */
public class ZListPageView extends ZListRecyclerView implements IPageList{

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

    private void addListener(){
        addSwipeRefreshListener();
        addLoadNextListener();
    }

    private void addLoadNextListener(){
        if(onLoadListener==null){
            addOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LayoutManager layoutManager = getLayoutManager();
                    int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    //lastVisibleItem>=totalItemCount-2表示滚动到最后2条，dy>0表示向下滚动
                    if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                        showPageNext();
                    }
                }
            });
        }
    }

    private void addSwipeRefreshListener(){
        if(httpRequestUtils!=null){
            ZSwipeRefreshLayout swipeRefresh = httpRequestUtils.getSwipeRefreshLayout();
            if(swipeRefresh!=null){
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
        if(httpRequestUtils!=null){
            httpRequestUtils.exec(page.getFirstPage());
        }
    }

    @Override
    public void showPageNext() {
        if(httpRequestUtils!=null){
            httpRequestUtils.exec(page.getNextPage());
        }
    }

}
