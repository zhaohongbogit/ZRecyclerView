package com.zongfi.zrecycleview.frame;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zongfi.zrecycleview.frame.page.Page;
import com.zongfi.zrecycleview.parse.BaseParser;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by ZHZEPHI on 2015/10/16.
 */
public class HttpRequestUtils {

    private Context context;
    private BaseParser parser;
    private ZBaseAdapter adapter;
    private ZListPageView recyclerView;
    private ZBaseAdapter.OnItemClickListener onItemClickListener;

    ZSwipeRefreshLayout swipeRefreshLayout;
    SlideInBottomAnimationAdapter myAdapter;

    private Page page;
    private int pageIndex = 1;

    public HttpRequestUtils(Context context, BaseParser parser,ZBaseAdapter adapter,ZListPageView recyclerView,ZBaseAdapter.OnItemClickListener onItemClickListener){
        this.context = context;
        this.parser = parser;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.onItemClickListener = onItemClickListener;
    }

    public void exec(Page page,int pageIndex){
        this.page = page;
        this.pageIndex = pageIndex;
        new InitDataLoad().execute();
    }

    class InitDataLoad extends AsyncTask {

        @Override
        protected void onPreExecute() {
            if (parser.isRunning) {
                cancel(true);
            }
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            Log.e("=============",String.valueOf(pageIndex));
            if(pageIndex!=1){
                page.setPageIndex();
            }
            return parser.parse(pageIndex);
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList data = (ArrayList) o;
            if (pageIndex==1) {
                adapter.setOnItemClickListener(onItemClickListener);
                myAdapter = new SlideInBottomAnimationAdapter(adapter);
                recyclerView.setAdapter(myAdapter);
            }
            if (data != null && data.size() > 0) {
                adapter.addDatas(data);
                myAdapter.notifyDataSetChanged();
            }
            if(swipeRefreshLayout!=null){
                swipeRefreshLayout.setRefreshing(false);
            }
            super.onPostExecute(o);
        }

    }

    public void setSwipeRefreshLayout(ZSwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public ZSwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

}
