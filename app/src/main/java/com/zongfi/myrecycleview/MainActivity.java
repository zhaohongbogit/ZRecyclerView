package com.zongfi.myrecycleview;

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
import com.zongfi.myrecycleview.widget.Divider;
import com.zongfi.myrecycleview.widget.ZAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends AppCompatActivity implements ZAdapter.OnItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @ViewInject(R.id.main_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.listView)
    RecyclerView recyclerView;

    List<String> data;
    SlideInBottomAnimationAdapter myAdapter;
    LinearLayoutManager layoutManager;

    ZAdapter adapter;
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

        //增加列表外文件
//        View headerView = getLayoutInflater().inflate(R.layout.main_header,null);
//        recyclerView.addView(headerView,0);

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
                new InitDataLoad().execute();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(page>2){
                    return;
                }
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
        Toast.makeText(this, data.get(position), Toast.LENGTH_SHORT).show();
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
        protected Object doInBackground(Object[] params) {
            String url = "http://news.ifeng.com/listpage/7129/"+page+"/list.shtmll";
            try {
                Document doc = Jsoup.connect(url).get();
                Elements units = doc.getElementsByClass("comListBox");
                Iterator<Element> items = units.iterator();
                if(page==1){
                    data = new ArrayList<String>();
                }
                while (items.hasNext()){
                    Element ele = items.next();
                    Elements as = ele.getElementsByTag("a");
                    data.add((data.size()+1)+"、"+as.first().text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(adapter==null || page==1){
                adapter = new ZAdapter();
                adapter.setDatas(data);
                adapter.setOnItemClickListener(MainActivity.this);
                myAdapter = new SlideInBottomAnimationAdapter(adapter);
                recyclerView.setAdapter(myAdapter);

            }
            myAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(o);
        }
    }
}
