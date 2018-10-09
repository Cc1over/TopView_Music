package com.hebaiyi.www.topviewmusic.music.presenter;

import android.os.Handler;
import android.os.Message;

import com.hebaiyi.www.topviewmusic.base.presenter.BasePresenter;
import com.hebaiyi.www.topviewmusic.bean.Lyrics;
import com.hebaiyi.www.topviewmusic.music.contract.LyricsContract;
import com.hebaiyi.www.topviewmusic.music.model.LyricsModel;
import com.hebaiyi.www.topviewmusic.util.LyricsDecoder;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class LyricsPresenterImp extends BasePresenter<LyricsContract.LyricsView>
        implements LyricsContract.LyricsPresenter {

    private static final int OBTAIN_LYRICS_SUCCESS = 0X8541;
    private static final int OBTAIN_LYRICS_FAIL = 0XCCCD;

    private LyricsModel mModel;
    private LyricsContract.LyricsView mView;
    private MusicHandler mHandler;

    public LyricsPresenterImp(LyricsContract.LyricsView view) {
        mView = view;
        mModel = new LyricsModel();
        mHandler = new MusicHandler(this);
    }

    @Override
    public void obtainLyrics(String path) {
        mModel.loadLyrics(path, new LyricsModel.LyricsCallback() {
            @Override
            public void onSuccess(String data) {
                decode(data);
            }

            @Override
            public void onFail() {
                Message msg = Message.obtain();
                msg.what = OBTAIN_LYRICS_FAIL;
                mHandler.sendMessage(msg);
            }
        });
    }

    private void decode(String lyrics) {
        try {
            LyricsDecoder decoder = new LyricsDecoder();
            List<Lyrics> lyricses = decoder.decodeLyrics(lyrics);
            Message msg = Message.obtain();
            msg.what = OBTAIN_LYRICS_SUCCESS;
            msg.obj = lyricses;
            mHandler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(OBTAIN_LYRICS_FAIL);
        }
    }

    private static class MusicHandler extends Handler {

        private final WeakReference<LyricsPresenterImp> mpRef;

        private MusicHandler(LyricsPresenterImp imp) {
            mpRef = new WeakReference<>(imp);
        }

        @Override
        public void handleMessage(Message msg) {
            LyricsPresenterImp lpi = mpRef.get();
            if (lpi == null) {
                return;
            }
            LyricsContract.LyricsView lv = lpi.mView;
            switch (msg.what) {
                case OBTAIN_LYRICS_SUCCESS:
                    lv.showLyrics((List<Lyrics>) msg.obj);
                    break;
                case OBTAIN_LYRICS_FAIL:
                    lv.showLyrics(null);
                    break;
            }
        }
    }

}
