package com.zongfi.zrecycleview.frame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHZEPHI on 2015/10/15.
 */
public abstract class ZBaseAdapter<T, V> extends RecyclerView.Adapter<ZBaseViewHolder> {

    protected Context context;
    protected OnItemClickListener onItemClickListener;
    protected List<T> datas;

    public ZBaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ZBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, initResource(), null);
        return getVH(view);
    }

    @Override
    public void onBindViewHolder(final ZBaseViewHolder holder, final int position) {
        //设置点击事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        getView(position, (V) holder);
    }

    protected abstract int initResource();

    protected abstract ZBaseViewHolder getVH(View view);

    protected abstract void getView(int position, V vh);

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void addDatas(List<T> data) {
        if (datas == null) {
            datas = new ArrayList<T>();
        }
        datas.addAll(data);
    }
}
