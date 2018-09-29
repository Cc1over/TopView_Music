package com.hebaiyi.www.topviewmusic.search.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.BaseAdapter;
import com.hebaiyi.www.topviewmusic.base.adapter.CommonViewHolder;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;

import java.util.List;

public class AlbumInfoAdapter extends BaseAdapter<SearchMerge.AlbumInfo> {

    private Context context;

    public AlbumInfoAdapter(Context context, List<SearchMerge.AlbumInfo> list) {
        super(list, R.layout.album_info_list_item);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void renewListItem(CommonViewHolder viewHolder,
                              SearchMerge.AlbumInfo albumInfo) {
        ImageView iv = viewHolder.getView(R.id.list_item_iv);
        TextView name = viewHolder.getView(R.id.list_item_tv_name);
        TextView st = viewHolder.getView(R.id.list_item_tv_singer_time);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.search_album_default);
        Glide.with(context)
                .load(albumInfo.getPicSmall())
                .apply(options)
                .into(iv);
        name.setText(Html.fromHtml(albumInfo.getTitle()));
        st.setText(Html.fromHtml(albumInfo.getAuthor())+" "+albumInfo.getPublishtime());
    }
}
