package com.hebaiyi.www.topviewmusic.base.activity;

import android.os.Bundle;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;

public abstract class PresenterActivity<V,P extends BasePresenter<V>> extends BottomActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenter();

    protected final P obtainPresenter(){
        return mPresenter;
    }


}
