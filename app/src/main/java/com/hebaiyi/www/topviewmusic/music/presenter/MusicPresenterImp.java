package com.hebaiyi.www.topviewmusic.music.presenter;

import android.os.Handler;
import android.os.Message;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.music.contract.MusicContract;
import com.hebaiyi.www.topviewmusic.music.model.MusicModel;

import java.lang.ref.WeakReference;

public class MusicPresenterImp extends BasePresenter<MusicContract.MusicView>
                             implements MusicContract.MusicPresenter {

    private static final int OBTAIN_LYRICS_SUCCESS = 0X8541;
    private static final int OBTAIN_LYRICS_FAIL = 0XCCCD;

    private MusicModel mModel;
    private MusicContract.MusicView mView;
    private MusicHandler mHandler;

    public MusicPresenterImp(MusicContract.MusicView view){
        mView = view;
        mModel = new MusicModel();
        mHandler = new MusicHandler(this);
    }

    private static class MusicHandler extends Handler{

        private final WeakReference<MusicPresenterImp> mpRef;

        private MusicHandler(MusicPresenterImp imp){
            mpRef = new WeakReference<>(imp);
        }

        @Override
        public void handleMessage(Message msg) {
            MusicPresenterImp mpi = mpRef.get();
            if(mpi==null){
                return;
            }
            MusicContract.MusicView mv = mpi.mView;
            switch (msg.what){
                case OBTAIN_LYRICS_SUCCESS:
                    break;
                case OBTAIN_LYRICS_FAIL:
                    break;
            }
        }
    }

}
