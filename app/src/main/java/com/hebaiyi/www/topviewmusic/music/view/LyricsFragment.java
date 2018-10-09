package com.hebaiyi.www.topviewmusic.music.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.fragment.BaseFragment;
import com.hebaiyi.www.topviewmusic.bean.Lyrics;
import com.hebaiyi.www.topviewmusic.music.contract.LyricsContract;
import com.hebaiyi.www.topviewmusic.music.presenter.LyricsPresenterImp;
import com.hebaiyi.www.topviewmusic.music.service.MusicManager;
import com.hebaiyi.www.topviewmusic.widget.LyricsView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LyricsFragment
        extends BaseFragment<LyricsContract.LyricsView, LyricsPresenterImp>
        implements LyricsContract.LyricsView {

    private static final int mPalyTimerDuration = 1000;
    private LyricsView mLyricsView;
    private String path;
    private Timer mTimer;
    private TimerTask mTask;
    private MusicActivity mParentActivity;
    private MusicManager mManager;
    private MusicManager.MusicObserver mObserver;

    @Override
    protected LyricsPresenterImp createPresenter() {
        return new LyricsPresenterImp(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParentActivity = (MusicActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentActivity = null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_lyrics;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mLyricsView = (LyricsView) findViewById(R.id.lyrics_lyrics_view_content);
        if (path != null && !"".equals(path)) {
            obtainPresenter().obtainLyrics(path);
        }
        mManager.attach(mObserver);
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        if (getArguments() != null) {
            path = getArguments().getString("lyrics_path", null);
        }
        mManager = MusicManager.getInstance();
        mObserver = new MusicManager.MusicObserver() {
            @Override
            public void OnPrepare() {
                if (mTimer == null) {
                    mTimer = new Timer();
                    mTask = new LyricsTask();
                    mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                }
            }

            @Override
            public void onComplete() {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
            }
        };
    }

    @Override
    public void showLyrics(List<Lyrics> lyricses) {
        mLyricsView.attach(this);
        mLyricsView.setLrc(lyricses);
    }

    public void replace() {
        mParentActivity.replaceFragment();
    }

    private class LyricsTask extends TimerTask {

        @Override
        public void run() {
            final long timePassed = mManager.getCurrentPosition();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mLyricsView.seekLrcToTime(timePassed);
                }
            });
            mLyricsView.setListener(new LyricsView.ILrcViewListener() {
                @Override
                public void onLrcSeeked(int position, Lyrics lrcRow) {
                    mManager.setCurrTime((int) lrcRow.getTime());
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.detach(mObserver);
    }
}
