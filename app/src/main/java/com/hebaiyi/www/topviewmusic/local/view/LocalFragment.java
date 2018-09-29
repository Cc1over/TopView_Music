package com.hebaiyi.www.topviewmusic.local.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.fragment.BaseFragment;
import com.hebaiyi.www.topviewmusic.bean.HotGeDan;
import com.hebaiyi.www.topviewmusic.local.adapter.GeDanAdapter;
import com.hebaiyi.www.topviewmusic.local.contract.LocalContract;
import com.hebaiyi.www.topviewmusic.local.presenter.LocalPresenterImp;
import com.hebaiyi.www.topviewmusic.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class LocalFragment extends
        BaseFragment<LocalContract.LocalView, LocalPresenterImp>
        implements View.OnClickListener, LocalContract.LocalView {

    private static final int HOT_GEDAN_NUM = 6;

    private RelativeLayout mRlytMusic;
    private RelativeLayout mRlytLruPlay;
    private RelativeLayout mRlytDownloadManagement;
    private RelativeLayout mRlytMyRadio;
    private RelativeLayout mRlytMyCollection;
    private RecyclerView mRcvRecommendList;
    private SwipeRefreshLayout mSrlytFresh;

    private GeDanAdapter mAdapter;
    private List<HotGeDan> mHotGeDans;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_local;
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRlytMusic = (RelativeLayout) findViewById(R.id.local_rlyt_music);
        mRlytLruPlay = (RelativeLayout) findViewById(R.id.local_rlyt_lru_play);
        mRlytDownloadManagement = (RelativeLayout) findViewById(R.id.local_rlyt_download_management);
        mRlytMyRadio = (RelativeLayout) findViewById(R.id.local_rlyt_my_radio);
        mRlytMyCollection = (RelativeLayout) findViewById(R.id.local_rlyt_my_collection);
        mRcvRecommendList = (RecyclerView) findViewById(R.id.local_rcv_recommend_list);
        mSrlytFresh = (SwipeRefreshLayout) findViewById(R.id.local_swipeRefresh_lyt_refresh);
        initSwipeRefreshLayout();
        // 注册监听
        mRlytMusic.setOnClickListener(this);
        mRlytLruPlay.setOnClickListener(this);
        mRlytDownloadManagement.setOnClickListener(this);
        mRlytMyRadio.setOnClickListener(this);
        mRlytMyCollection.setOnClickListener(this);
        // 请求数据
        obtainPresenter().obtainHotGeDan(HOT_GEDAN_NUM);
    }

    private void initSwipeRefreshLayout() {
        mSrlytFresh.setColorSchemeResources(R.color.red);
        mSrlytFresh.setRefreshing(true);
        mSrlytFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showHotGeDan(mHotGeDans);
                mSrlytFresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.local_rlyt_music:
                LocalMusicListActivity.actionStart(getApplicationContext());
                break;
            case R.id.local_rlyt_lru_play:
                break;
            case R.id.local_rlyt_download_management:
                break;
            case R.id.local_rlyt_my_radio:
                break;
            case R.id.local_rlyt_my_collection:
                break;
        }
    }


    @Override
    public void showHotGeDan(List<HotGeDan> hotGeDans) {
        if (mHotGeDans == null) {
            if (hotGeDans == null) {
                mRcvRecommendList.setVisibility(View.INVISIBLE);
                mSrlytFresh.setRefreshing(false);
                return;
            } else {
                mHotGeDans = new ArrayList<>(hotGeDans);
            }
        }
        if (mAdapter == null) {
            if (CollectionUtil.isEmpty(hotGeDans)) {
                mRcvRecommendList.setVisibility(View.INVISIBLE);
                return;
            }
            GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            mRcvRecommendList.setLayoutManager(manager);
            mAdapter = new GeDanAdapter(getApplicationContext(), hotGeDans.subList(0, 3));
            mRcvRecommendList.setAdapter(mAdapter);
        } else {
            if (!CollectionUtil.isEmpty(hotGeDans)) {
                if (mAdapter.getData().contains(hotGeDans.subList(0, 3).get(0))) {
                    mAdapter.changeData(hotGeDans.subList(3, 6));
                } else {
                    mAdapter.changeData(hotGeDans.subList(0, 3));
                }
            }
        }
        mSrlytFresh.setRefreshing(false);
    }

    @Override
    protected LocalPresenterImp createPresenter() {
        return new LocalPresenterImp(this);
    }

}
