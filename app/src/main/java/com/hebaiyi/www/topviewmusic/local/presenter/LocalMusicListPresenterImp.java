package com.hebaiyi.www.topviewmusic.local.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.bean.LocalMusic;
import com.hebaiyi.www.topviewmusic.local.contract.LocalMusicListContract;
import com.hebaiyi.www.topviewmusic.local.model.LocalMusicListModel;

import java.lang.ref.WeakReference;
import java.util.List;


public class LocalMusicListPresenterImp
        extends BasePresenter<LocalMusicListContract.LocalMusicListView>
        implements LocalMusicListContract.LocalMusicListPresenter {

    private static final int OBTAIN_LIST_SUCCESS = 0XCCDD;
    private static final int OBTAIN_LIST_FAIL = 0X8FDC;

    private LocalMusicListContract.LocalMusicListView mView;
    private LocalMusicListModel mModel;
    private LocalMusicListHandler mHandler;

    public LocalMusicListPresenterImp(LocalMusicListContract.LocalMusicListView view){
        mView = view;
        mModel = new LocalMusicListModel();
        mHandler = new LocalMusicListHandler(this);
    }

    public void obtainLocalMusicList(Context context){
        mModel.loadLocalMusicList(context, new LocalMusicListModel.LocalMusicListCallback() {
            @Override
            public void onFail(Exception e) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_LIST_FAIL;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onSuccess(List<LocalMusic> localList) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_LIST_SUCCESS;
                msg.obj = localList;
                mHandler.sendMessage(msg);
            }
        });
    }


    private static class LocalMusicListHandler extends Handler {

        private final WeakReference<LocalMusicListPresenterImp> lmRef;

        private LocalMusicListHandler(LocalMusicListPresenterImp imp) {
            lmRef = new WeakReference<>(imp);
        }

        @Override
        public void handleMessage(Message msg) {
            LocalMusicListPresenterImp imp = lmRef.get();
            if (imp == null) {
                return;
            }
            LocalMusicListContract.LocalMusicListView v = imp.mView;
            switch (msg.what) {
                case OBTAIN_LIST_SUCCESS:
                    v.showLocalMusicList((List<LocalMusic>) msg.obj);
                    break;
                case OBTAIN_LIST_FAIL:
                    v.showLocalMusicList(null);
                    break;
            }
        }
    }

}
