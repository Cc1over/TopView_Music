package com.hebaiyi.www.topviewmusic.recommend.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.fragment.BaseFragment;
import com.hebaiyi.www.topviewmusic.bean.FoucsPic;
import com.hebaiyi.www.topviewmusic.bean.GeDan;
import com.hebaiyi.www.topviewmusic.bean.RecommendRadio;
import com.hebaiyi.www.topviewmusic.bean.Song;
import com.hebaiyi.www.topviewmusic.recommend.adapter.ContentAdapter;
import com.hebaiyi.www.topviewmusic.recommend.contract.RecommendContract;
import com.hebaiyi.www.topviewmusic.recommend.presenter.RecommendPresenterImp;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecommendFragment
        extends BaseFragment<RecommendContract.
        RecommendView, RecommendPresenterImp> implements RecommendContract.RecommendView {

    private static final int REQUEST_SONG_LIST = 15;
    private static final int REQUEST_GEDAN = 18;
    private static final int REQUEST_RADIO = 17;

    private static final int HEADER_VIEWS_NUM = 8;
    private static final int RECOMMEND_SONG_NUM = 6;
    private static final int GEDAN_NUM = 6;
    private static final int RECOMMEND_RADIO_NUM = 6;

    private RecyclerView mRcvContent;
    private SwipeRefreshLayout mSrlytFresh;
    private ContentAdapter mAdapter;
    private RecommendHandler mHandler;

    @Override
    protected RecommendPresenterImp createPresenter() {
        return new RecommendPresenterImp(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        mHandler = new RecommendHandler(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRcvContent = (RecyclerView) findViewById(R.id.recommend_rcv_content);
        mSrlytFresh = (SwipeRefreshLayout) findViewById(R.id.recommend_srlyt_fresh);
        initSwipeRefreshLayout();
        mAdapter = new ContentAdapter(getApplicationContext());
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
        mRcvContent.setLayoutManager(manager);
        mRcvContent.setAdapter(mAdapter);
        obtainPresenter().obtainFoucsPic(HEADER_VIEWS_NUM);
    }

    private void initSwipeRefreshLayout() {
        mSrlytFresh.setColorSchemeResources(R.color.red);
        mSrlytFresh.setRefreshing(true);
        mSrlytFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlytFresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showFoucsPic(final List<FoucsPic> pics) {
        if (pics == null) {
            return;
        }
        mAdapter.setHeader(HEADER_VIEWS_NUM, pics);
        mHandler.sendEmptyMessage(REQUEST_GEDAN);
    }

    @Override
    public void showRecommendSongList(List<Song> songs) {
        if (songs == null) {
            return;
        }
        mAdapter.addSongs(songs);
        mHandler.sendEmptyMessage(REQUEST_RADIO);
    }

    @Override
    public void showGeDan(List<GeDan> geDans) {
        if (geDans == null) {
            return;
        }
        mAdapter.addGeDan(geDans);
        mHandler.sendEmptyMessage(REQUEST_SONG_LIST);
    }

    @Override
    public void showRadio(List<RecommendRadio> radios) {
        if (radios == null) {
            return;
        }
        mAdapter.addRecommendRadio(radios);
        mSrlytFresh.setRefreshing(false);
    }

    private static class RecommendHandler extends Handler {

        private WeakReference<RecommendFragment> mRfRef;

        private RecommendHandler(RecommendFragment rf) {
            mRfRef = new WeakReference<>(rf);
        }

        @Override
        public void handleMessage(Message msg) {
            RecommendFragment rf = mRfRef.get();
            if (rf == null) {
                return;
            }
            switch (msg.what) {
                case REQUEST_GEDAN:
                    rf.obtainPresenter().obtainGeDan(GEDAN_NUM, 1);
                    break;
                case REQUEST_SONG_LIST:
                    rf.obtainPresenter().obtainRecommendSongList(RECOMMEND_SONG_NUM);
                    break;
                case REQUEST_RADIO:
                    rf.obtainPresenter().obtainRecommendRadio(RECOMMEND_RADIO_NUM);
                    break;
                default:
                    break;
            }
        }
    }

}
