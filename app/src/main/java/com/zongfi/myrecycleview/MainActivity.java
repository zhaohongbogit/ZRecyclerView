package com.zongfi.myrecycleview;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zongfi.myrecycleview.network.ZHttpUtils;
import com.zongfi.myrecycleview.parse.ParseBox;
import com.zongfi.myrecycleview.parse.ParseNews;
import com.zongfi.myrecycleview.pojo.News;
import com.zongfi.myrecycleview.widget.Divider;
import com.zongfi.myrecycleview.widget.ZAdapter;
import com.zongfi.myrecycleview.widget.ZNewAdapter;
import com.zongfi.myrecycleview.widget.ZRecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends AppCompatActivity implements ZNewAdapter.OnItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @ViewInject(R.id.main_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.listView)
    ZRecyclerView recyclerView;

    SlideInBottomAnimationAdapter myAdapter;
    LinearLayoutManager layoutManager;

    ZNewAdapter adapter;
    Integer page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        initSwife();

        //设置布局
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new Divider());
        recyclerView.setEmptyView(View.inflate(MainActivity.this,R.layout.main_header,null));

        new InitDataLoad().execute();
    }

    private void initSwife(){
//        swipeRefreshLayout.setColorSchemeResources(R.color.swife_color1,R.color.swife_color2,R.color.swife_color3,R.color.swife_color4,R.color.swife_color5);
//        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
//        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                adapter = null;
                new InitDataLoad().execute();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if(page>2){
//                    return;
//                }
                int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    page++;
                    new InitDataLoad().execute();
                }
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
            if(ParseNews.getInstance().isRunning){
                cancel(true);
            }
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
//            data = ParseBox.getInstance().parse(page);
            return ParseNews.getInstance().parse(page);
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList<News> data = (ArrayList<News>) o;
            if(adapter==null){
                adapter = new ZNewAdapter(MainActivity.this);
                adapter.setOnItemClickListener(MainActivity.this);
                myAdapter = new SlideInBottomAnimationAdapter(adapter);
                recyclerView.setAdapter(myAdapter);
            }
            if(data!=null && data.size()>0){
                adapter.addDatas(data);
                myAdapter.notifyDataSetChanged();
            }
            swipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(o);
        }
    }
}
