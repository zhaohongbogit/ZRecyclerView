package com.zongfi.zrecycleview;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zongfi.zrecycleview.frame.HttpRequestUtils;
import com.zongfi.zrecycleview.frame.ZListPageView;
import com.zongfi.zrecycleview.parse.ParseNews;
import com.zongfi.zrecycleview.pojo.News;
import com.zongfi.zrecycleview.avh.adapter.NewsAdapter;
import com.zongfi.zrecycleview.frame.ZListRecyclerView;
import com.zongfi.zrecycleview.frame.ZRecyclerView;
import com.zongfi.zrecycleview.frame.ZSwipeRefreshLayout;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @ViewInject(R.id.main_swipe)
    ZSwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.listView)
    ZListPageView recyclerView;

    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initSwife();
    }

    private void initSwife() {
        recyclerView.setEmptyView(View.inflate(MainActivity.this, R.layout.main_empty, null));
        adapter = new NewsAdapter(MainActivity.this);
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(MainActivity.this,new ParseNews(),adapter,recyclerView,this);
        httpRequestUtils.setSwipeRefreshLayout(swipeRefreshLayout);
        recyclerView.setHttpRequestUtils(httpRequestUtils);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.showPageFirst();
            }
        });
        recyclerView.showPageFirst();
    }

    @Override
    public void onItemClick(View view, int position) {
        News news = (News) adapter.getDatas().get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getSourceUrl()));
        startActivity(intent);
    }

}
