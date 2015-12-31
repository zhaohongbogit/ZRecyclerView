package com.zongfi.zrecycleview.adapter;

import android.content.Context;
import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import org.zhzephi.recycler.adapter.ZBaseAdapter;
import org.zhzephi.recycler.viewholder.ZBaseViewHolder;
import com.zongfi.zrecycleview.R;
import com.zongfi.zrecycleview.viewholder.NewsViewHolder;
import com.zongfi.zrecycleview.pojo.News;

/**
 * Created by ZHZEPHI on 2015/9/25.
 */
public class NewsAdapter extends ZBaseAdapter<News,NewsViewHolder> {

    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int initResource() {
        return R.layout.main_item;
    }

    @Override
    protected ZBaseViewHolder getVH(View view) {
        return new NewsViewHolder(view);
    }

    @Override
    protected void getView(int position, NewsViewHolder vh) {
        News news = datas.get(position);
        vh.mTitle.setText(news.getTitle());
        new BitmapUtils(context).display(vh.mImage, news.getImgPath());
    }

}
