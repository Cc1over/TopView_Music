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

import java.lang.ref.WeakReference;
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
    private long currTime;
    private boolean isStart = true;

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
        mLyricsView.setListener(new LyricsView.ILrcViewListener() {
            @Override
            public void onLrcSeeked(int position, Lyrics lrcRow) {
                currTime = lrcRow.getTime();
                mManager.setCurrTime((int) lrcRow.getTime());
                mParentActivity.setCurrTime(lrcRow.getTime());
            }
        });
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        if (getArguments() != null) {
            path = getArguments().getString("lyrics_path", null);
        }
        mManager = MusicManager.getInstance();
        if (mTimer == null) {
            mTimer = new Timer();
            mTask = new LyricsTask();
            mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
        }
        mObserver = new MusicManager.MusicObserver() {
            @Override
            public void OnPrepare() {
            }

            @Override
            public void onComplete() {
                currTime = 0;
            }
        };
        mManager.attach(mObserver);
    }

    public void setCurrTime(long currTime) {
        this.currTime = currTime;
        if (mLyricsView != null) {
            mLyricsView.seekLrcToTime(currTime);
        }
    }

    public void isLyricsStart(boolean isStart) {
        this.isStart = isStart;
    }

    @Override
    public void showLyrics(List<Lyrics> lyricses) {
        mLyricsView.attach(this);
        mLyricsView.setLyrics(lyricses);
        mLyricsView.seekLrcToTime(currTime);
    }

    public void replace() {
        mParentActivity.replaceFragment();
    }

    private  class LyricsTask extends TimerTask {

        @Override
        public void run() {
            if (isStart) {
                currTime += mPalyTimerDuration;
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        mLyricsView.seekLrcToTime(currTime);
                    }
                });
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.detach(mObserver);
    }

}
