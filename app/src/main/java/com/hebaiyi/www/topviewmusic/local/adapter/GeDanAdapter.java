package com.hebaiyi.www.topviewmusic.local.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.BaseAdapter;
import com.hebaiyi.www.topviewmusic.base.adapter.CommonViewHolder;
import com.hebaiyi.www.topviewmusic.bean.HotGeDan;

import java.util.List;

public class GeDanAdapter extends BaseAdapter<HotGeDan> {

    private final int TYPE_HEADER = 0X489;
    private final int TYPE_NORMAL = 0X787;

    private Context context;

    public GeDanAdapter(Context context, List<HotGeDan> list) {
        super(list, R.layout.local_recommend_list_item);
        getData().add(0, null);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recommend_list_item_header, null);
            return new CommonViewHolder(view);
        }
        if (viewType == TYPE_NORMAL) {
            return super.onCreateViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void renewListItem(CommonViewHolder viewHolder, HotGeDan hotGeDan) {
        ImageView iv = viewHolder.getView(R.id.local_list_item_iv_pic);
        Glide.with(context).load(hotGeDan.getPicture()).into(iv);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (!(manager instanceof GridLayoutManager)) {
            return;
        }
        final GridLayoutManager gridManager = (GridLayoutManager) manager;
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (getItemViewType(position)) {
                    case TYPE_HEADER:
                        return 3;
                    case TYPE_NORMAL:
                        return 1;
                    default:
                        return 0;
                }
            }
        });
    }

    @Override
    public void changeData(List<HotGeDan> datas) {
        for (int i = 0; i < datas.size(); i++) {
            getData().remove(i + 1);
            getData().add(i + 1, datas.get(i));
            notifyItemChanged(i + 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }


}
