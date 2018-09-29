package com.hebaiyi.www.topviewmusic.recommend.presenter;

import android.os.Handler;
import android.os.Message;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.bean.Channel;
import com.hebaiyi.www.topviewmusic.recommend.contract.ChannelContract;
import com.hebaiyi.www.topviewmusic.recommend.model.ChannelModel;

import java.lang.ref.WeakReference;
import java.util.List;


public class ChannelPresenterImp
        extends BasePresenter<ChannelContract.ChannelView>
        implements ChannelContract.ChannelPresenter {

    private static final int OBTAIN_CHANNEL_FAIL = 0X4545;
    private static final int OBTAIN_CHANNEL_SUCCESS = 0X6966;

    private ChannelModel mModel;
    private ChannelContract.ChannelView mView;
    private ArticleHandler mHandler;

    public ChannelPresenterImp(ChannelContract.ChannelView view) {
        mModel = new ChannelModel();
        mHandler = new ArticleHandler(this);
        mView = view;
    }

    @Override
    public void obtainChannel(int pageNo,int pageSize) {
         mModel.loadChannel(pageNo, pageSize, new ChannelModel.ChannelCallback() {
             @Override
             public void onFail(Exception e) {
                 Message msg = Message.obtain();
                 msg.what = OBTAIN_CHANNEL_FAIL;
                 mHandler.sendMessage(msg);
             }

             @Override
             public void onSuccess(List<Channel> channels) {
                 Message msg = Message.obtain();
                 msg.what = OBTAIN_CHANNEL_SUCCESS;
                 msg.obj = channels;
                 mHandler.sendMessage(msg);
             }
         });
    }

    private static class ArticleHandler extends Handler {

        private final WeakReference<ChannelPresenterImp> arRef;

        private ArticleHandler(ChannelPresenterImp af) {
            arRef = new WeakReference<>(af);
        }

        @Override
        public void handleMessage(Message msg) {
            ChannelPresenterImp af = arRef.get();
            if (af == null) {
                return;
            }
            ChannelContract.ChannelView cv = af.mView;
            switch (msg.what) {
                case OBTAIN_CHANNEL_SUCCESS:
                    cv.showChannel((List<Channel>) msg.obj);
                    break;
                case OBTAIN_CHANNEL_FAIL:
                    cv.showChannel(null);
                    break;
                default:
                    break;
            }
        }
    }


}
