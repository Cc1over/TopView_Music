package com.hebaiyi.www.topviewmusic.recommend.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.fragment.BaseFragment;
import com.hebaiyi.www.topviewmusic.bean.Billboard;
import com.hebaiyi.www.topviewmusic.recommend.adapter.BillboardAdapter;
import com.hebaiyi.www.topviewmusic.recommend.contract.BillboardContract;
import com.hebaiyi.www.topviewmusic.recommend.presenter.BillboardPresenterImp;

import java.util.List;

public class BillboardFragment
        extends BaseFragment<BillboardContract.BillboardView, BillboardPresenterImp>
        implements BillboardContract.BillboardView {

    private SwipeRefreshLayout mSrflytFresh;
    private RecyclerView mRcvContent;


    @Override
    protected BillboardPresenterImp createPresenter() {
        return new BillboardPresenterImp(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_billboard;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSrflytFresh = (SwipeRefreshLayout) findViewById(R.id.billboard_srflyt_fresh);
        mRcvContent = (RecyclerView) findViewById(R.id.billboard_rcv_content);
        initSwipeRefreshLayout();
        obtainPresenter().obtainBillboard();
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

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {

    }

    @Override
    public void showBillboard(List<Billboard> billboards) {
        if (billboards == null) {
            return;
        }
        BillboardAdapter adapter = new BillboardAdapter(billboards, getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRcvContent.setLayoutManager(manager);
        mRcvContent.setAdapter(adapter);
        mSrflytFresh.setRefreshing(false);
    }

}
