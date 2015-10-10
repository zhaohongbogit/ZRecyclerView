package com.zongfi.myrecycleview.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zongfi.myrecycleview.R;
import com.zongfi.myrecycleview.pojo.News;

import java.util.List;

/**
 * Created by ZHZEPHI on 2015/9/25.
 */
public class ZNewAdapter extends RecyclerView.Adapter<ZNewAdapter.ZViewHolder>{

    Context context;
    OnItemClickListener onItemClickListener;
    List<News> datas;

    public ZNewAdapter(Context context){
        this.context = context;
    }

    @Override
    public ZViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.main_item, null);
        return new ZViewHolder(view);
    }

    public List<News> getDatas() {
        return datas;
    }

    public void setDatas(List<News> datas) {
        this.datas = datas;
    }

    @Override
    public void onBindViewHolder(final ZViewHolder holder, final int position) {
        News news = datas.get(position);
        holder.mTitle.setText(news.getTitle());
        new BitmapUtils(context).display(holder.mImage,news.getImgPath());

        //设置点击事件
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ZViewHolder extends RecyclerView.ViewHolder{

        @ViewInject(R.id.main_item_title)
        TextView mTitle;
        @ViewInject(R.id.main_item_img)
        ImageView mImage;

        public ZViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
