package com.hebaiyi.www.topviewmusic.recommend.presenter;

import android.os.Handler;
import android.os.Message;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.bean.FoucsPic;
import com.hebaiyi.www.topviewmusic.bean.GeDan;
import com.hebaiyi.www.topviewmusic.bean.RecommendRadio;
import com.hebaiyi.www.topviewmusic.bean.Song;
import com.hebaiyi.www.topviewmusic.recommend.contract.RecommendContract;
import com.hebaiyi.www.topviewmusic.recommend.model.RecommendModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecommendPresenterImp
        extends BasePresenter<RecommendContract.RecommendView>
        implements RecommendContract.RecommendPresenter {

    private static final int OBTAIN_FOUCS_PIC_SUCCESS = 0X5845;
    private static final int OBTAIN_FOUCS_PIC_FAIL = 0X68998;

    private static final int OBTAIN_SONG_LIST_SUCCESS = 0X5656;
    private static final int OBTAIN_SONG_LIST_FAIL = 0X5611;

    private static final int OBTAIN_GEDAN_SUCCESS = 0X9896;
    private static final int OBTAIN_GEDAN_FAIL = 0XD17;

    private static final int OBTAIN_RADIO_SUCCESS = 0XD5C6;
    private static final int OBTAIN_RADIO_FAIL = 0X1463D;

    private RecommendContract.RecommendView mView;
    private RecommendModel mModel;
    private RecommendHandler mHandler;

    public RecommendPresenterImp(RecommendContract.RecommendView view) {
        mView = view;
        mHandler = new RecommendHandler(this);
        mModel = new RecommendModel();
    }

    @Override
    public void obtainFoucsPic(int num) {
        mModel.loadFoucsPic(num, new RecommendModel.FoucsPicCallback() {
            @Override
            public void onFail(Exception e) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_FOUCS_PIC_FAIL;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onSuccess(List<FoucsPic> pics) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_FOUCS_PIC_SUCCESS;
                msg.obj = pics;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void obtainRecommendSongList(int num) {
        mModel.loadRecommendSongList(num, new RecommendModel.RecommendSongCallback() {
            @Override
            public void onFail(Exception e) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_FOUCS_PIC_FAIL;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onSuccess(List<Song> songs) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_SONG_LIST_SUCCESS;
                msg.obj = songs;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void obtainGeDan(int num, int pageNo) {
        mModel.loadGeDan(num, pageNo, new RecommendModel.GeDanCallback() {
            @Override
            public void onSuccess(List<GeDan> geDans) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_GEDAN_SUCCESS;
                msg.obj = geDans;
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

    @Override
    public void obtainRecommendRadio(int num) {
        mModel.loadRecommendRadio(num, new RecommendModel.RecommendRadioCallback() {
            @Override
            public void onFail(Exception e) {
                Message msg = Message.obtain();
                msg.what = OBTAIN_RADIO_FAIL;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onSuccess(List<RecommendRadio> radios) {
                Message msg = Message.obtain();
                msg.obj = radios;
                msg.what = OBTAIN_RADIO_SUCCESS;
                mHandler.sendMessage(msg);
            }
        });
    }

    private static class RecommendHandler extends Handler {

        private final WeakReference<RecommendPresenterImp> mPresenterRef;

        private RecommendHandler(RecommendPresenterImp imp) {
            mPresenterRef = new WeakReference<>(imp);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RecommendPresenterImp rpi = mPresenterRef.get();
            if (rpi == null) {
                return;
            }
            RecommendContract.RecommendView v = rpi.mView;
            switch (msg.what) {
                case OBTAIN_FOUCS_PIC_SUCCESS:
                    v.showFoucsPic((List<FoucsPic>) msg.obj);
                    break;
                case OBTAIN_FOUCS_PIC_FAIL:
                    v.showFoucsPic(null);
                    break;
                case OBTAIN_SONG_LIST_SUCCESS:
                    v.showRecommendSongList((List<Song>) msg.obj);
                    break;
                case OBTAIN_SONG_LIST_FAIL:
                    v.showFoucsPic(null);
                    break;
                case OBTAIN_GEDAN_SUCCESS:
                    v.showGeDan((List<GeDan>) msg.obj);
                    break;
                case OBTAIN_GEDAN_FAIL:
                    v.showGeDan(null);
                    break;
                case OBTAIN_RADIO_SUCCESS:
                    v.showRadio((List<RecommendRadio>) msg.obj);
                    break;
                case OBTAIN_RADIO_FAIL:
                    v.showRadio(null);
                    break;
                default:
                    break;
            }

        }
    }
}

