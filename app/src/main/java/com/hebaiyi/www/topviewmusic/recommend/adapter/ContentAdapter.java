package com.hebaiyi.www.topviewmusic.recommend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.WebViewActivity;
import com.hebaiyi.www.topviewmusic.base.adapter.CommonViewHolder;
import com.hebaiyi.www.topviewmusic.bean.FoucsPic;
import com.hebaiyi.www.topviewmusic.bean.GeDan;
import com.hebaiyi.www.topviewmusic.bean.RecommendRadio;
import com.hebaiyi.www.topviewmusic.bean.Song;
import com.hebaiyi.www.topviewmusic.util.ViewUtil;
import com.hebaiyi.www.topviewmusic.widget.Banner;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 0X5656;

    private static final int GEDAN_TITLE = 0X1111;
    private static final int TYPE_GEDAN = 0X8887;

    private static final int SONG_LIST_TITLE = 0X8989;
    private static final int TYPE_SONG = 0X899;

    private static final int RADIO_TITLE = 0X9565;
    private static final int TYPE_RADIO = 0XC6D5;

    private boolean isInit = false;
    private List<Song> mSongs;
    private List<GeDan> mGeDans;
    private List<RecommendRadio> mRadios;

    // recyclerView Item数量
    private Context context;

    private Header mHeader;

    public ContentAdapter(Context context) {
        this.context = context;
        mHeader = new Header();
        mSongs = new ArrayList<>();
        mGeDans = new ArrayList<>();
        mRadios = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                mHeader.view = new Banner(context);
                return new CommonViewHolder(mHeader.view);
            // 歌单部分
            case GEDAN_TITLE:
                return new CommonViewHolder(createTitleTextView());
            case TYPE_GEDAN:
                @SuppressLint("InflateParams") View gv = LayoutInflater
                        .from(context).inflate(R.layout.recommend_list_item_view, null);
                return new CommonViewHolder(gv);
            // 最新音乐部分
            case SONG_LIST_TITLE:
                return new CommonViewHolder(createTitleTextView());
            case TYPE_SONG:
                @SuppressLint("InflateParams") View sv = LayoutInflater
                        .from(context).inflate(R.layout.recommend_list_item_view, null);
                return new CommonViewHolder(sv);
            // 主播电台部分
            case RADIO_TITLE:
                return new CommonViewHolder(createTitleTextView());
            case TYPE_RADIO:
                @SuppressLint("InflateParams") View rv = LayoutInflater
                        .from(context).inflate(R.layout.recommend_list_item_view, null);
                return new CommonViewHolder(rv);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommonViewHolder commHolder = (CommonViewHolder) holder;
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                if (!isInit) {
                    initBanner(commHolder);
                }
                break;
            case GEDAN_TITLE:
                setTitle("推荐歌单", commHolder);
                break;
            case TYPE_GEDAN:
                if (mGeDans.size() != 0) {
                    GeDan geDan = mGeDans.get(position - geDanTitlePosition() - 1);
                    setItem(geDan.getTitle(), geDan.getPic300(), commHolder);
                }
                break;
            case SONG_LIST_TITLE:
                setTitle("最新音乐", commHolder);
                break;
            case TYPE_SONG:
                if (mSongs.size() != 0) {
                    Song song = mSongs.get(position - songListTitlePosition() - 1);
                    setItem(song.getTitle(), song.getPicPremium(), commHolder);
                }
                break;
            case RADIO_TITLE:
                setTitle("主播电台", commHolder);
                break;
            case TYPE_RADIO:
                if (mRadios.size() != 0) {
                    RecommendRadio radio = mRadios.get(position - radioTitlePosition() - 1);
                    setItem(radio.getTitle(), radio.getPicture(), commHolder);
                }
                break;
        }
    }

    private void setTitle(String title, CommonViewHolder commHolder) {
        TextView stv = commHolder.getView(R.id.title_text);
        stv.setText(title);
    }

    private void setItem(String text, String url, CommonViewHolder commHolder) {
        TextView tv = commHolder.getView(R.id.list_item_tv);
        ImageView iv = commHolder.getView(R.id.list_item_iv);
        Glide.with(context)
                .load(url)
                .into(iv);
        tv.setText(text);
    }

    public void addSongs(List<Song> songs) {
        mSongs.addAll(songs);
        for (int i = songListTitlePosition(); i <= songListTitlePosition() + mSongs.size(); i++) {
            notifyItemChanged(i);
        }
    }

    public void addRecommendRadio(List<RecommendRadio> radios) {
        mRadios.addAll(radios);
        for (int i = radioTitlePosition(); i <= radioTitlePosition() + mRadios.size(); i++) {
            notifyItemChanged(i);
        }
    }

    public void addGeDan(List<GeDan> geDans) {
        mGeDans.addAll(geDans);
        for (int i = geDanTitlePosition(); i <= geDanTitlePosition() + mGeDans.size(); i++) {
            notifyItemChanged(i);
        }
    }

    @Override
    public int getItemCount() {
        return 1 + (mGeDans.size() == 0 ? 0 : mGeDans.size() + 1)
                + (mSongs.size() == 0 ? 0 : mSongs.size() + 1)
                + (mRadios.size() == 0 ? 0 : mRadios.size() + 1);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == geDanTitlePosition()) {
            return GEDAN_TITLE;
        }
        if (position >= geDanTitlePosition() + 1
                && position <= geDanTitlePosition() + mGeDans.size()) {
            return TYPE_GEDAN;
        }
        if (position == songListTitlePosition()) {
            return SONG_LIST_TITLE;
        }
        if (position >= songListTitlePosition() + 1
                && position <= songListTitlePosition() + mSongs.size()) {
            return TYPE_SONG;
        }
        if (position == radioTitlePosition()) {
            return RADIO_TITLE;
        }
        if (position >= radioTitlePosition() + 1
                && position <= radioTitlePosition() + mRadios.size()) {
            return TYPE_RADIO;
        }
        return super.getItemViewType(position);
    }


    /**
     * 设置轮播图数量
     *
     * @param num 数量
     */
    public void setHeader(int num, List<FoucsPic> pics) {
        mHeader.num = num;
        mHeader.datas = pics;
        notifyDataSetChanged();
    }

    /**
     * 初始化banner视图呈现轮播图
     */
    private void initBanner(CommonViewHolder holder) {
        if (mHeader.num == 0 || mHeader.datas == null) {
            mHeader.view.setVisibility(View.GONE);
            return;
        } else {
            mHeader.view.setVisibility(View.VISIBLE);
        }
        Banner banner = (Banner) holder.itemView;
        banner.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        banner.getLayoutParams().height = ViewUtil.dip2px(context, 200);
        banner.setSize(ViewUtil.dip2px(context, 10));
        banner.setSpacing(ViewUtil.dip2px(context, 5));
        final List<FoucsPic> pics = mHeader.datas;
        int num = mHeader.num;
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < pics.size(); i++) {
            urls.add(pics.get(i).getRandpic());
        }
        banner.setCount(num);
        banner.loadData(urls);
        banner.display();
        banner.cancel();
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                WebViewActivity
                        .actionStart(context, pics.get(position).getCode(), "");
            }
        });
        isInit = true;
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
                    case GEDAN_TITLE:
                        return 3;
                    case TYPE_GEDAN:
                        return 1;
                    case SONG_LIST_TITLE:
                        return 3;
                    case TYPE_SONG:
                        return 1;
                    case RADIO_TITLE:
                        return 3;
                    case TYPE_RADIO:
                        return 1;
                    default:
                        return 0;
                }
            }
        });
    }

    private int geDanTitlePosition() {
        return 1;
    }

    private int songListTitlePosition() {
        return geDanTitlePosition() + mGeDans.size() + 1;
    }

    private int radioTitlePosition() {
        return songListTitlePosition() + mSongs.size() + 1;
    }

    @SuppressLint("InflateParams")
    private TextView createTitleTextView() {
        return (TextView) LayoutInflater
                .from(context).inflate(R.layout.recommend_list_item_title, null);
    }

    private class Header {
        private Banner view;
        private int num;
        private List<FoucsPic> datas;
    }

}
