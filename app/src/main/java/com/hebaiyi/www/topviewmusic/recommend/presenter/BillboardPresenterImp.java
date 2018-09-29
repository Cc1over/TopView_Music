package com.hebaiyi.www.topviewmusic.recommend.presenter;

import android.os.Handler;
import android.os.Message;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.bean.Billboard;
import com.hebaiyi.www.topviewmusic.recommend.contract.BillboardContract;
import com.hebaiyi.www.topviewmusic.recommend.model.BillboardModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class BillboardPresenterImp
        extends BasePresenter<BillboardContract.BillboardView>
        implements BillboardContract.BillboardPresenter {

    private static final int OBTAIN_BILLBOARD_FAIL = 0X8945;
    private static final int OBTAIN_BILLBOARD_SUCCESS = 0XC8D9;

    private BillboardModel mModel;
    private BillboardContract.BillboardView mView;
    private BillboardHandler mHandler;

    public BillboardPresenterImp(BillboardContract.BillboardView view) {
        mModel = new BillboardModel();
        mView = view;
        mHandler = new BillboardHandler(this);
    }

    @Override
    public void obtainBillboard() {
         mModel.loadBillboard(new BillboardModel.BillboardCallback() {
             @Override
             public void onFail(Exception e) {
                  Message msg = Message.obtain();
                  msg.what = OBTAIN_BILLBOARD_FAIL;
                  mHandler.sendMessage(msg);
             }

             @Override
             public void onSuccess(List<Billboard> billboards) {
                 Message msg = Message.obtain();
                 msg.what = OBTAIN_BILLBOARD_SUCCESS;
                 msg.obj = billboards;
                 mHandler.sendMessage(msg);
             }
         });
    }


    private static class BillboardHandler extends Handler {

        private WeakReference<BillboardPresenterImp> brRef;

        private BillboardHandler(BillboardPresenterImp imp) {
            brRef = new WeakReference<>(imp);
        }

        @Override
        public void handleMessage(Message msg) {
            BillboardPresenterImp brp = brRef.get();
            if (brp == null) {
                return;
            }
            BillboardContract.BillboardView v = brp.mView;
            switch (msg.what) {
                case OBTAIN_BILLBOARD_FAIL:
                    v.showBillboard(null);
                    break;
                case OBTAIN_BILLBOARD_SUCCESS:
                    v.showBillboard((List<Billboard>) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }


}
