package com.zongfi.zrecycleview.avh.vh;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.zongfi.zrecycleview.R;
import com.zongfi.zrecycleview.frame.ZBaseViewHolder;

/**
 * Created by ZHZEPHI on 2015/10/15.
 */
public class NewsViewHolder extends ZBaseViewHolder {

    @ViewInject(R.id.main_item_title)
    public TextView mTitle;
    @ViewInject(R.id.main_item_img)
    public ImageView mImage;

    public NewsViewHolder(View itemView) {
        super(itemView);
    }
}
