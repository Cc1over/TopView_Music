package com.hebaiyi.www.topviewmusic.search.adpater;

import android.app.DownloadManager;
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

public class ArtistInfoAdapter extends BaseAdapter<SearchMerge.ArtistInfo> {

    private Context context;

    public ArtistInfoAdapter(Context context,List<SearchMerge.ArtistInfo> list) {
        super(list, R.layout.artist_info_list_item);
        this.context = context;
    }

    @Override
    public void renewListItem(CommonViewHolder viewHolder, SearchMerge.ArtistInfo artistInfo) {
        ImageView iv = viewHolder.getView(R.id.list_item_iv);
        TextView author = viewHolder.getView(R.id.list_item_tv_author);
        TextView country = viewHolder.getView(R.id.list_item_tv_country);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.search_article_defalut);
        Glide.with(context)
                .load(artistInfo.getAvatarMiddle())
                .apply(options)
                .into(iv);
        author.setText(Html.fromHtml(artistInfo.getAuthor()));
        country.setText(artistInfo.getCountry());
    }
}
