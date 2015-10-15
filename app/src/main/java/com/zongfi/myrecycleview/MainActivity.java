package com.zongfi.myrecycleview;

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
import com.zongfi.myrecycleview.parse.ParseNews;
import com.zongfi.myrecycleview.pojo.News;
import com.zongfi.myrecycleview.widget.ZNewAdapter;
import com.zongfi.myrecycleview.zWidget.ZListRecyclerView;
import com.zongfi.myrecycleview.zWidget.ZRecyclerView;
import com.zongfi.myrecycleview.zWidget.ZSwipeRefreshLayout;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends AppCompatActivity implements ZNewAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @ViewInject(R.id.main_swipe)
    ZSwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.listView)
    ZListRecyclerView recyclerView;

    SlideInBottomAnimationAdapter myAdapter;

    ZNewAdapter adapter;
    Integer page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        initSwife();
        new InitDataLoad().execute();
    }

    private void initSwife() {
        recyclerView.setEmptyView(View.inflate(MainActivity.this, R.layout.main_header, null));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                adapter = null;
                new InitDataLoad().execute();
            }
        });
        recyclerView.setOnLoadListener(new ZRecyclerView.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                new InitDataLoad().execute();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(this, data.get(position), Toast.LENGTH_SHORT).show();
        News news = (News) adapter.getDatas().get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getSourceUrl()));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class InitDataLoad extends AsyncTask {

        @Override
        protected void onPreExecute() {
            if (ParseNews.getInstance().isRunning) {
                cancel(true);
            }
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            return ParseNews.getInstance().parse(page);
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList<News> data = (ArrayList<News>) o;
            if (adapter == null) {
                adapter = new ZNewAdapter(MainActivity.this);
                adapter.setOnItemClickListener(MainActivity.this);
                myAdapter = new SlideInBottomAnimationAdapter(adapter);
                recyclerView.setAdapter(myAdapter);
            }
            if (data != null && data.size() > 0) {
                adapter.addDatas(data);
                myAdapter.notifyDataSetChanged();
            }
            swipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(o);
        }
    }
}
