package com.hebaiyi.www.topviewmusic.search.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.LoadMoreWrapper;
import com.hebaiyi.www.topviewmusic.base.fragment.LazyFragment;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.search.adpater.SongInfoAdapter;
import com.hebaiyi.www.topviewmusic.search.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class SongInfoFragment extends LazyFragment {

    private RecyclerView mRcvContent;
    private List<SearchMerge.SongInfo> mSongInfos;
    private ResultFragment mParentFragment;
    private LoadMoreWrapper mWrapper;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_songinfo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRcvContent = (RecyclerView) findViewById(R.id.song_info_rcv_content);
        mParentFragment = (ResultFragment) getParentFragment();
        if (mParentFragment.obtainSongInfos() != null) {
            mSongInfos.addAll(mParentFragment.obtainSongInfos());
        }
        initRecyclerView();
    }

    @Subscribe(sticky = true)
    public void handleRecyclerView(SearchContract.MergeSet ms) {
        int state = mParentFragment.obtainCurrState();
        if (state == SearchActivity.SEARCH_RESET) {
            mSongInfos.clear();
            mSongInfos.addAll(ms.getSongInfos());
            mWrapper.notifyDataSetChanged();
        }
        if (state == SearchActivity.SEARCH_READD) {
            mWrapper.setDataChange(ms.getSongInfos());
        }
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        mSongInfos = new ArrayList<>();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        SongInfoAdapter adapter = new SongInfoAdapter(mSongInfos);
        adapter.setItemClickListener(new SongInfoAdapter.SongInfoItemClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(mSongInfos.get(position));
            }
        });
        mWrapper = new LoadMoreWrapper(adapter, getApplicationContext());
        mWrapper.addOnLoadMoreListener(new LoadMoreWrapper.LoadMoreScrollListener() {
            @Override
            public void onLoadMore() {
                mParentFragment.loadMore();
            }
        });
        mRcvContent.setLayoutManager(manager);
        mRcvContent.setAdapter(mWrapper);
    }

}
