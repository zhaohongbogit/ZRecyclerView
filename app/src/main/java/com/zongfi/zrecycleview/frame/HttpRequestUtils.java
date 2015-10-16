package com.zongfi.zrecycleview.frame;

import android.content.Context;
import android.os.AsyncTask;

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

    private int page = 1;

    public HttpRequestUtils(Context context, BaseParser parser,ZBaseAdapter adapter,ZListPageView recyclerView,ZBaseAdapter.OnItemClickListener onItemClickListener){
        this.context = context;
        this.parser = parser;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.onItemClickListener = onItemClickListener;
    }

    public void exec(int pageIndex){
        this.page = pageIndex;
        new InitDataLoad().execute(pageIndex);
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
            return parser.parse((int)params[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList data = (ArrayList) o;
            if (page==1) {
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
}
