package com.hebaiyi.www.topviewmusic.base.presenter;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> {

    protected WeakReference<T> mViewRef;

    public void attachView(T view){
        mViewRef = new WeakReference<>(view);
    }

    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public T getView(){
        return mViewRef.get();
    }


}
