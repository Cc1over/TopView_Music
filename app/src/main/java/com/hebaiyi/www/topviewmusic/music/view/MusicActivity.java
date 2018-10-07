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
import android.widget.TextView;
import android.widget.Toast;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.BottomActivity;
import com.hebaiyi.www.topviewmusic.base.activity.PresenterActivity;
import com.hebaiyi.www.topviewmusic.bean.Music;
import com.hebaiyi.www.topviewmusic.music.contract.MusicContract;
import com.hebaiyi.www.topviewmusic.music.presenter.MusicPresenterImp;
import com.hebaiyi.www.topviewmusic.music.service.MusicManager;
import com.hebaiyi.www.topviewmusic.util.ToastUtil;

import de.greenrobot.event.Subscribe;

public class MusicActivity
        extends BottomActivity implements View.OnClickListener {

    public static final String RECEIVER_ACTION = "com.hebaiyi.www.music.MusicActivity.RECEIVER";
    public static final int MODE_LIST_LOOP = 0;
    public static final int MODE_SINGER_LOOP = 1;
    public static final int MODE_SHUFFLE_PLAYBACK = 2;
    public static final int NEXT_SONG = 0X55EF;
    public static final int PAST_SONG = 0X10FF;
    public static int currMode = MODE_LIST_LOOP;
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
    private MusicManager mManager;

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
        setData();
        initToolbar(mTbTitle, R.drawable.back_icon);
        replaceFragment();
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
            if(mMusic.getDuration()!=0){
                mTvTotalTime.setText(mMusic.getDuration());
            }
        }
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
        currMode++;
        if (currMode == mModeDrawable.length) {
            currMode = 0;
        }
        mIvMode.setImageResource(mModeDrawable[currMode]);
        switch (currMode) {
            case MODE_LIST_LOOP:
                ToastUtil.showToast("列表循环", Toast.LENGTH_SHORT);
                break;
            case MODE_SINGER_LOOP:
                ToastUtil.showToast("单曲循环", Toast.LENGTH_SHORT);
                break;
            case MODE_SHUFFLE_PLAYBACK:
                ToastUtil.showToast("随机播放", Toast.LENGTH_SHORT);
                break;
        }
    }

    private void showList() {

    }

    private void nextSong() {
        Intent i = new Intent(RECEIVER_ACTION);
        i.putExtra("operate", NEXT_SONG);
        sendBroadcast(i);
    }

    private void pastSong() {
        Intent i = new Intent(RECEIVER_ACTION);
        i.putExtra("operate", PAST_SONG);
        sendBroadcast(i);
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
    }

    @Override
    protected void loadData() {

    }

    @Subscribe(sticky = true, priority = 1)
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
    }

    @Override
    protected void getBottomState(Music music) {

    }

    private void replaceFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currFragment == FRAGMENT_PHOTO) {
            transaction.replace(R.id.music_flyt_center, mLyricsFragment);
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

}
