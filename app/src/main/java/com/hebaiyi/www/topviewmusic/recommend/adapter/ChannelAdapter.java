package com.hebaiyi.www.topviewmusic.recommend.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.BaseAdapter;
import com.hebaiyi.www.topviewmusic.base.adapter.CommonViewHolder;
import com.hebaiyi.www.topviewmusic.bean.Channel;

import java.util.List;

public class ChannelAdapter extends BaseAdapter<Channel> {

    private Context context;

    public ChannelAdapter(List<Channel> list, Context context) {
        super(list, R.layout.channel_list_item);
        this.context = context;
    }

    @Override
    public void renewListItem(CommonViewHolder viewHolder, Channel channel) {
        ImageView iv = viewHolder.getView(R.id.list_item_iv);
        TextView tvName = viewHolder.getView(R.id.list_item_tv_name);
        TextView tvCateSname = viewHolder.getView(R.id.list_item_tv_cate_sname);
        Glide.with(context).load(channel.getPicture()).into(iv);
        tvName.setText(channel.getName());
        tvCateSname.setText(channel.getCateSname());
    }

}
