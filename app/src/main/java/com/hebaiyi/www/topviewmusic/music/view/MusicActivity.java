package com.hebaiyi.www.topviewmusic.music.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.BottomActivity;
import com.hebaiyi.www.topviewmusic.bean.Music;
import com.hebaiyi.www.topviewmusic.music.service.MusicManager;
import com.hebaiyi.www.topviewmusic.music.service.SongManager;
import com.hebaiyi.www.topviewmusic.util.TimeUtil;
import com.hebaiyi.www.topviewmusic.util.ToastUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.Subscribe;

public class MusicActivity
        extends BottomActivity implements View.OnClickListener {

    public int currPostition;
    private static final int FRAGMENT_PHOTO = 0XCCCC;
    private static final int FRAGMENT_LYRICS = 0XDDDD;
    private Toolbar mTbTitle;
    private ImageView mIvMode, mIvPlay, mIvList, mIvNext, mIvPast;
    private FrameLayout mFlytCenter;
    private int[] mModeDrawable = {R.drawable.music_list_loop,
            R.drawable.music_single_loop, R.drawable.music_shuffle_playback};
    private Music mMusic;
    private boolean isPlaying;
    private int currFragment;
    private PhotoFragment mPhotoFragment;
    private LyricsFragment mLyricsFragment;
    private TextView mTvName, mTvSinger;
    private TextView mTvCurrTime, mTvTotalTime;
    private SeekBar mSbProgress;
    private MusicManager mManager;
    private Timer mTimer;
    private MusicManager.MusicObserver mObserver;
    private ProgressTask mTask;
    private long currTime;
    private SongManager mSongManager;

    public static void actionStart(Context context, Music m) {
        Intent i = new Intent(context, MusicActivity.class);
        i.putExtra("music", m);
        context.startActivity(i);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_music);
        setTranslucentStatus();
        mTbTitle = findViewById(R.id.music_tb_title);
        mIvList = findViewById(R.id.music_iv_list);
        mIvMode = findViewById(R.id.music_iv_mode);
        mIvNext = findViewById(R.id.music_iv_next);
        mIvPast = findViewById(R.id.music_iv_past);
        mIvPlay = findViewById(R.id.music_iv_play);
        mFlytCenter = findViewById(R.id.music_flyt_center);
        mTvName = findViewById(R.id.music_tv_name);
        mTvSinger = findViewById(R.id.music_tv_singer);
        mTvCurrTime = findViewById(R.id.music_tv_curr_time);
        mTvTotalTime = findViewById(R.id.music_tv_total_time);
        mSbProgress = findViewById(R.id.music_seek_bar_progress);
        setData();
        initToolbar(mTbTitle, R.drawable.back_icon);
        replaceFragment();
        mSbProgress.setMax(100);
    }

    private void setData() {
        if (mMusic != null) {
            mTvName.setText(mMusic.getName());
            mTvSinger.setText(mMusic.getSinger());
            mPhotoFragment.setPhoto(mMusic.getPicUrl());
            isPlaying = mMusic.isPlaying();
            if (isPlaying) {
                mIvPlay.setImageResource(R.drawable.music_playing);
            } else {
                mIvPlay.setImageResource(R.drawable.music_play);
            }
            if (mManager.getDuration() != -1) {
                mTvTotalTime.setText(TimeUtil.conversionToStr(mManager.getDuration()));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSbProgress.setProgress((int) mManager.getProgress());
        currTime = getCurrProgress(mSbProgress);
        mLyricsFragment.setCurrTime(currTime);
        mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_iv_list:
                showList();
                break;
            case R.id.music_iv_mode:
                changeMode();
                break;
            case R.id.music_iv_next:
                nextSong();
                break;
            case R.id.music_iv_past:
                pastSong();
                break;
            case R.id.music_iv_play:
                processPlayback();
                break;
            case R.id.music_flyt_center:
                replaceFragment();
                break;
        }
    }

    private void changeMode() {
        currPostition++;
        List<Integer> mode = mSongManager.ObtainModeList();
        if (currPostition == mode.size()) {
            currPostition = 0;
        }
        mSongManager.changeMode(mode.get(currPostition));
        mIvMode.setImageResource(mModeDrawable[currPostition]);
        switch (currPostition) {
            case SongManager.MODE_LIST_LOOP:
                ToastUtil.showToast("列表循环", Toast.LENGTH_SHORT);
                break;
            case SongManager.MODE_SINGER_LOOP:
                ToastUtil.showToast("单曲循环", Toast.LENGTH_SHORT);
                break;
            case SongManager.MODE_SHUFFLE_PLAYBACK:
                ToastUtil.showToast("随机播放", Toast.LENGTH_SHORT);
                break;
        }
    }

    private void showList() {

    }

    private void nextSong() {
        mSongManager.nextSong(true);
        mIvPlay.setImageResource(R.drawable.music_playing);
        isPlaying = true;
        mPhotoFragment.setRotate(isPlaying);
        currTime = 0;
        mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
    }

    private void pastSong() {
        mSongManager.pastSong();
        mIvPlay.setImageResource(R.drawable.music_playing);
        isPlaying = true;
        mPhotoFragment.setRotate(isPlaying);
        currTime = 0;
        mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
    }

    private void processPlayback() {
        if (isPlaying) {
            mIvPlay.setImageResource(R.drawable.music_play);
            isPlaying = false;
        } else {
            mIvPlay.setImageResource(R.drawable.music_playing);
            isPlaying = true;
        }
        mPhotoFragment.setRotate(isPlaying);
        if (!isPlaying) {
            mManager.pause();
        } else {
            mManager.start();
        }
        mLyricsFragment.isLyricsStart(isPlaying);
    }

    @Override
    protected void initVariables() {
        mPhotoFragment = new PhotoFragment();
        mLyricsFragment = new LyricsFragment();
        mMusic = getIntent().getParcelableExtra("music");
        if (mMusic != null) {
            isPlaying = mMusic.isPlaying();
        }
        mManager = MusicManager.getInstance();
        mSongManager = SongManager.getInstance();
        if (mTimer == null) {
            mTimer = new Timer();
            mTask = new ProgressTask();
            mTimer.scheduleAtFixedRate(mTask, 0, 10);
        }
        mObserver = new MusicManager.MusicObserver() {
            @Override
            public void OnPrepare() {
                isPlaying = true;
            }

            @Override
            public void onComplete() {
                isPlaying = false;
                currTime = 0;
                mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
            }
        };
        mManager.attach(mObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mManager.detach(mObserver);
    }

    @Override
    protected void loadData() {

    }

    @Subscribe(sticky = true)
    public void onChangeEvent(Music music) {
        mMusic = music;
        setData();
    }

    @Override
    protected void initEvents() {
        mFlytCenter.setOnClickListener(this);
        mIvList.setOnClickListener(this);
        mIvMode.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mIvPast.setOnClickListener(this);
        mIvPlay.setOnClickListener(this);
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    currTime = getCurrProgress(seekBar);
                    mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(seekBar.getProgress());
                currTime = getCurrProgress(seekBar);
                mLyricsFragment.setCurrTime(currTime);
                mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
                mManager.setCurrTime((int) currTime);
            }
        });
    }

    private long getCurrProgress(SeekBar seekBar) {
        float x = seekBar.getProgress() * 1.0f / 100;
        return (long) (x * mManager.getDuration());
    }

    @Override
    protected void getBottomState(Music music) {

    }

    public void replaceFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currFragment == FRAGMENT_PHOTO) {
            transaction.replace(R.id.music_flyt_center, mLyricsFragment);
            Bundle data = new Bundle();
            data.putString("lyrics_path", mMusic.getLyrics());
            mLyricsFragment.setArguments(data);
            currFragment = FRAGMENT_LYRICS;
            transaction.commit();
            return;
        }
        if (currFragment == FRAGMENT_LYRICS || currFragment == 0) {
            transaction.replace(R.id.music_flyt_center, mPhotoFragment);
            currFragment = FRAGMENT_PHOTO;
            Bundle data = new Bundle();
            data.putString("url", mMusic.getPicUrl());
            mPhotoFragment.setArguments(data);
            transaction.commit();
        }
    }

    @Override
    protected Music setBottomState() {
        mMusic.setPlaying(isPlaying);
        return mMusic;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    private class ProgressTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isPlaying) {
                        mSbProgress.setProgress((int) mManager.getProgress());
                        currTime += 10;
                        mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
                    }
                }
            });
        }
    }

    public void setCurrTime(long currTime) {
        this.currTime = currTime;
        mTvCurrTime.setText(TimeUtil.conversionToStr(currTime));
    }

}
