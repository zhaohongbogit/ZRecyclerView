package com.zongfi.zrecycleview.frame;

import android.content.Context;
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
    }

    public ZListPageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHttpRequestUtils(HttpRequestUtils httpRequestUtils) {
        this.httpRequestUtils = httpRequestUtils;
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
