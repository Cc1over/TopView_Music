package com.hebaiyi.www.topviewmusic.local.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.bean.HotGeDan;
import com.hebaiyi.www.topviewmusic.local.contract.LocalContract;
import com.hebaiyi.www.topviewmusic.local.model.LocalModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class LocalPresenterImp extends BasePresenter<LocalContract.LocalView>
        implements LocalContract.LocalPresenter {

    private static final int OBTAIN_GEDAN_SUCCESS = 0X5489;
    private static final int OBTAIN_GEDAN_FAIL = 0X18548;

    private LocalModel mModel;
    private LocalContract.LocalView mView;
    private LocalHandler mHandler;

    public LocalPresenterImp(LocalContract.LocalView view) {
        mModel = new LocalModel();
        mView = view;
        mHandler = new LocalHandler(this);
    }

    @Override
    public void obtainHotGeDan(int num) {
        mModel.loadHotGeDan(num, new LocalModel.HotGeDanCallback() {
            @Override
            public void onSuccess(List<HotGeDan> hotGeDans) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_GEDAN_SUCCESS;
                msg.obj = hotGeDans;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFail(Exception e) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_GEDAN_FAIL;
                mHandler.sendMessage(msg);
            }
        });
    }


    private static class LocalHandler extends Handler {

        private final WeakReference<LocalPresenterImp> mPresenterImp;

        private LocalHandler(LocalPresenterImp presenterImp) {
            super(Looper.getMainLooper());
            mPresenterImp = new WeakReference<>(presenterImp);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LocalPresenterImp lpi = mPresenterImp.get();
            if (lpi == null) {
                return;
            }
            LocalContract.LocalView v = lpi.mView;
            switch (msg.what) {
                case OBTAIN_GEDAN_SUCCESS:
                    v.showHotGeDan((List<HotGeDan>) msg.obj);
                    break;
                case OBTAIN_GEDAN_FAIL:
                    v.showHotGeDan(null);
                    break;
                default:
                    break;
            }

        }
    }

}
