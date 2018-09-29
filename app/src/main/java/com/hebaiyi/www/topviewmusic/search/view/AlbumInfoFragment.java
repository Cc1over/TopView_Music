package com.hebaiyi.www.topviewmusic.search.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.adapter.LoadMoreWrapper;
import com.hebaiyi.www.topviewmusic.base.fragment.LazyFragment;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.search.adpater.AlbumInfoAdapter;
import com.hebaiyi.www.topviewmusic.search.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;

public class AlbumInfoFragment extends LazyFragment {

    private RecyclerView mRcvContent;
    private List<SearchMerge.AlbumInfo> mAlbumInfos;
    private ResultFragment mParentFragment;
    private LoadMoreWrapper mWrapper;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_albuminfo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRcvContent = (RecyclerView) findViewById(R.id.album_info_rcv_content);
        mParentFragment = (ResultFragment) getParentFragment();
        if (mParentFragment.obtainAlbumInfos() != null) {
            mAlbumInfos.addAll(mParentFragment.obtainAlbumInfos());
        }
        initRecyclerView();
    }

    @Subscribe
    public void handleRecyclerView(SearchContract.MergeSet ms) {
        int state = mParentFragment.obtainCurrState();
        if (state == SearchActivity.SEARCH_RESET) {
            mAlbumInfos.clear();
            mAlbumInfos.addAll(ms.getAlbumInfos());
            mWrapper.notifyDataSetChanged();
        }
        if (state == SearchActivity.SEARCH_READD) {
            mWrapper.setDataChange(ms.getAlbumInfos());
        }
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        mAlbumInfos = new ArrayList<>();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        AlbumInfoAdapter adapter = new AlbumInfoAdapter(getApplicationContext(), mAlbumInfos);
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
