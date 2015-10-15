package com.zongfi.zrecycleview.frame;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ZONGFI on 2015/5/23.
 */
public class ZDefaultDivider extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(22,16,16,22);
    }
}
