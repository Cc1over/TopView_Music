package com.hebaiyi.www.topviewmusic.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    private List<T> mList;
    private int mLayoutId;

    public BaseAdapter(List<T> list, int layoutId) {
        mList = list;
        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, null);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        T t = mList.get(position);
        CommonViewHolder commHolder = (CommonViewHolder) holder;
        // 更新UI
        renewListItem(commHolder, t);
    }

    protected int obtainLayoutId(){
        return mLayoutId;
    }

    public void changeData(List<T> datas){
        mList = datas;
    }

    public List<T> getData() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public abstract void renewListItem(CommonViewHolder viewHolder, T t);

}
