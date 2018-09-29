package com.hebaiyi.www.topviewmusic.recommend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.BaseAdapter;
import com.hebaiyi.www.topviewmusic.base.adapter.CommonViewHolder;
import com.hebaiyi.www.topviewmusic.bean.Billboard;
import com.hebaiyi.www.topviewmusic.bean.Content;

import java.util.List;

public class BillboardAdapter extends BaseAdapter<Billboard> {

    private Context context;

    private static final int TYPE_TITLE = 0XD4CC;

    public BillboardAdapter(List<Billboard> list, Context context) {
        super(list, R.layout.billboard_list_item);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
           View v = LayoutInflater
                    .from(context)
                   .inflate(R.layout.billboard_list_item_title, parent,false);
            return new CommonViewHolder(v);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_TITLE) {
            super.onBindViewHolder(holder, position - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void renewListItem(CommonViewHolder viewHolder, Billboard billboard) {
        ImageView iv = viewHolder.getView(R.id.list_item_iv);
        TextView tv1 = viewHolder.getView(R.id.list_item_tv_1);
        TextView tv2 = viewHolder.getView(R.id.list_item_tv_2);
        TextView tv3 = viewHolder.getView(R.id.list_item_tv_3);
        Glide.with(context).load(billboard.getPicS192()).into(iv);
        List<Content> contents = billboard.getContents();
        tv1.setText(new StringBuilder().append("1.")
                .append(contents.get(0).getTitle())
                .append("-")
                .append(contents.get(0).getAuthor()));
        tv2.setText(new StringBuilder().append("2.")
                .append(contents.get(1).getTitle())
                .append("-")
                .append(contents.get(1).getAuthor()));
        tv3.setText(new StringBuilder().append("3.")
                .append(contents.get(2).getTitle())
                .append("-")
                .append(contents.get(2).getAuthor()));
    }

}
