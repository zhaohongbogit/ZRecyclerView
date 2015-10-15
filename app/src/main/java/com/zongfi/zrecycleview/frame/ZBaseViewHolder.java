package com.zongfi.zrecycleview.frame;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lidroid.xutils.ViewUtils;

/**
 * Created by ZHZEPHI on 2015/10/15.
 */
public class ZBaseViewHolder extends RecyclerView.ViewHolder {

    public ZBaseViewHolder(View itemView) {
        super(itemView);
        ViewUtils.inject(this, itemView);
    }

}
