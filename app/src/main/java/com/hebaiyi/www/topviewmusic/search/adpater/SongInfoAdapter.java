package com.hebaiyi.www.topviewmusic.search.adpater;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.BaseAdapter;
import com.hebaiyi.www.topviewmusic.base.adapter.CommonViewHolder;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.search.view.SongInfoFragment;

import java.util.List;

public class SongInfoAdapter extends BaseAdapter<SearchMerge.SongInfo> {

    private SongInfoItemClickListener mListener;

    public SongInfoAdapter(List<SearchMerge.SongInfo> list) {
        super(list, R.layout.song_info_list_item);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(obtainLayoutId(), parent,false);
        return new CommonViewHolder(view);
    }

    @Override
    public void renewListItem(CommonViewHolder viewHolder,
                              SearchMerge.SongInfo songInfo) {
        TextView name = viewHolder.getView(R.id.list_item_tv_name);
        TextView singer = viewHolder.getView(R.id.list_item_tv_singer);
        name.setText(songInfo.getTitle());
        singer.setText(songInfo.getAuthor());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  mListener.onClick(position);
            }
        });
    }

    public interface SongInfoItemClickListener {
        void onClick(int position);
    }

    public void setItemClickListener(SongInfoItemClickListener listener){
        mListener = listener;
    }


}
