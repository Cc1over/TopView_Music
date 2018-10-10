package com.hebaiyi.www.topviewmusic.local.adapter;

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
import com.hebaiyi.www.topviewmusic.bean.LocalMusic;
import com.hebaiyi.www.topviewmusic.bean.Music;

import java.util.List;

public class LocalMusicListAdapter extends BaseAdapter<Music> {

    public final static int TYPE_HEADER = 0XCCDF;
    public final static int TYPE_NORMAL = 0XDDFF;

    private LocalMusicListListener mListener;

    public LocalMusicListAdapter(List<Music> list) {
        super(list, R.layout.local_music_list_item);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_local_music_list_header, parent, false);
            TextView tv = view.findViewById(R.id.header_tv);
            String text = String.valueOf(tv.getText()) +
                    "(" + getData().size() + ")";
            tv.setText(text);
            return new CommonViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(obtainLayoutId(), parent, false);
            return new CommonViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            super.onBindViewHolder(holder, position - 1);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemViewType(position) == TYPE_NORMAL) {
                    mListener.onClick(position - 1);
                }
                if (getItemViewType(position) == TYPE_HEADER) {
                    mListener.onClick(0);
                }
            }
        });
    }

    @Override
    public void renewListItem(CommonViewHolder viewHolder, Music music) {
        TextView name = viewHolder.getView(R.id.list_item_tv_name);
        TextView singer = viewHolder.getView(R.id.list_item_tv_singer);
        name.setText(music.getName());
        singer.setText(music.getSinger());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    public void setLocalMusicListListener(LocalMusicListListener listener) {
        mListener = listener;
    }

    public interface LocalMusicListListener {
        void onClick(int position);
    }

}
