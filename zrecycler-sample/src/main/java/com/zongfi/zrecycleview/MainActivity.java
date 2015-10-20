package com.zongfi.zrecycleview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhzephi.recycler.ZListPageView;
import com.zhzephi.recycler.network.HttpRequestUtils;
import com.zhzephi.recycler.widget.ZSwipeRefreshLayout;
import com.zongfi.zrecycleview.parse.ParseNews;
import com.zongfi.zrecycleview.pojo.News;
import com.zongfi.zrecycleview.adapter.NewsAdapter;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @ViewInject(R.id.main_swipe)
    ZSwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.listView)
    ZListPageView recyclerView;
    @ViewInject(R.id.empty)
    RelativeLayout emptyLayout;

    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        initSwife();
    }

    private void initSwife() {
        adapter = new NewsAdapter(MainActivity.this);
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(MainActivity.this,new ParseNews(),adapter,recyclerView,this);
        httpRequestUtils.setSwipeRefreshLayout(swipeRefreshLayout);
        recyclerView.setHttpRequestUtils(httpRequestUtils);
        recyclerView.setEmptyView(emptyLayout);
        recyclerView.showPageFirst();
    }

    @Override
    public void onItemClick(View view, int position) {
        News news = (News) adapter.getDatas().get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getSourceUrl()));
        startActivity(intent);
    }

}
