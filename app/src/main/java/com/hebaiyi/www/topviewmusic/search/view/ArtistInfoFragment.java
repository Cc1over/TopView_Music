package com.hebaiyi.www.topviewmusic.search.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.LoadMoreWrapper;
import com.hebaiyi.www.topviewmusic.base.fragment.LazyFragment;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.search.adpater.ArtistInfoAdapter;
import com.hebaiyi.www.topviewmusic.search.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;

public class ArtistInfoFragment extends LazyFragment {

    private RecyclerView mRcvContent;
    private List<SearchMerge.ArtistInfo> mArtistInfos;
    private LoadMoreWrapper mWrapper;
    private ResultFragment mParentFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_artistinfo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRcvContent = (RecyclerView) findViewById(R.id.artist_info_rcv_content);
        mParentFragment = (ResultFragment) getParentFragment();
        if (mParentFragment.obtainArtistInfos() != null) {
            mArtistInfos.addAll(mParentFragment.obtainArtistInfos());
        }
        initRecyclerView();
    }

    @Subscribe
    public void handleRecyclerView(SearchContract.MergeSet ms) {
        int state = mParentFragment.obtainCurrState();
        if (state == SearchActivity.SEARCH_RESET) {
            mArtistInfos.clear();
            mArtistInfos.addAll(ms.getArtistInfos());
            mWrapper.notifyDataSetChanged();
        }
        if (state == SearchActivity.SEARCH_READD) {
            mWrapper.setDataChange(ms.getArtistInfos());
        }
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        mArtistInfos = new ArrayList<>();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        ArtistInfoAdapter adapter = new ArtistInfoAdapter(getApplicationContext(), mArtistInfos);
        mRcvContent.setLayoutManager(manager);
        mWrapper = new LoadMoreWrapper(adapter, getApplicationContext());
        mWrapper.addOnLoadMoreListener(new LoadMoreWrapper.LoadMoreScrollListener() {
            @Override
            public void onLoadMore() {
                mParentFragment.loadMore();
            }
        });
        mRcvContent.setAdapter(mWrapper);
    }

}
