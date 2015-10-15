package com.zongfi.zrecycleview.avh.adapter;

import android.content.Context;
import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.zongfi.zrecycleview.R;
import com.zongfi.zrecycleview.avh.vh.NewsViewHolder;
import com.zongfi.zrecycleview.frame.ZBaseViewHolder;
import com.zongfi.zrecycleview.pojo.News;
import com.zongfi.zrecycleview.frame.ZBaseAdapter;

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
