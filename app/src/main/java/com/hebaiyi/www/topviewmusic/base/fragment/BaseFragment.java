package com.hebaiyi.www.topviewmusic.base.fragment;

import android.os.Bundle;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;

public abstract class BaseFragment<V,P extends BasePresenter<V>> extends LazyFragment {

    private P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected P obtainPresenter(){
        return mPresenter;
    }

    protected abstract P createPresenter();

}
