package com.hebaiyi.www.topviewmusic.recommend.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.fragment.BaseFragment;
import com.hebaiyi.www.topviewmusic.bean.Channel;
import com.hebaiyi.www.topviewmusic.recommend.adapter.ChannelAdapter;
import com.hebaiyi.www.topviewmusic.base.adapter.LoadMoreWrapper;
import com.hebaiyi.www.topviewmusic.recommend.contract.ChannelContract;
import com.hebaiyi.www.topviewmusic.recommend.presenter.ChannelPresenterImp;

import java.util.List;

public class ChannelFragment
        extends BaseFragment<ChannelContract.ChannelView, ChannelPresenterImp>
        implements ChannelContract.ChannelView {

    private static final int PAGE_SIZE = 10;
    private int currPageNo = 1;

    private RecyclerView mRcvContent;
    private SwipeRefreshLayout mSrflytFresh;
    private LoadMoreWrapper mWrapper;

    @Override
    protected ChannelPresenterImp createPresenter() {
        return new ChannelPresenterImp(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRcvContent = (RecyclerView) findViewById(R.id.channel_rcv_content);
        mSrflytFresh = (SwipeRefreshLayout) findViewById(R.id.channel_srflyt_fresh);
        initSwipeRefreshLayout();
        obtainPresenter().obtainChannel(currPageNo++, PAGE_SIZE);
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {

    }

    @Override
    public void showChannel(List<Channel> channels) {
        if(channels==null){
            return;
        }
        if (mWrapper == null) {
            initRecyclerView(channels);
        } else {
            mWrapper.setDataChange(channels);
        }
    }

    private void initRecyclerView(List<Channel> channels) {
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        final ChannelAdapter adapter = new ChannelAdapter(channels, getApplicationContext());
        mWrapper = new LoadMoreWrapper(adapter, getApplicationContext());
        mWrapper.addOnLoadMoreListener(new LoadMoreWrapper.LoadMoreScrollListener() {
            @Override
            public void onLoadMore() {
                obtainPresenter().obtainChannel(currPageNo++, PAGE_SIZE);
            }
        });
        mRcvContent.setLayoutManager(manager);
        mRcvContent.setAdapter(mWrapper);
        mSrflytFresh.setRefreshing(false);
    }

    private void initSwipeRefreshLayout() {
        mSrflytFresh.setColorSchemeResources(R.color.red);
        mSrflytFresh.setRefreshing(true);
        mSrflytFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrflytFresh.setRefreshing(false);
            }
        });
    }


}
