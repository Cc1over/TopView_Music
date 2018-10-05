package com.hebaiyi.www.topviewmusic.music.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.base.activity.PresenterActivity;
import com.hebaiyi.www.topviewmusic.bean.BottomMusic;
import com.hebaiyi.www.topviewmusic.bean.LocalMusic;
import com.hebaiyi.www.topviewmusic.music.contract.MusicContract;
import com.hebaiyi.www.topviewmusic.music.presenter.MusicPresenterImp;

public class MusicActivity
        extends PresenterActivity<MusicContract.MusicView, MusicPresenterImp>
        implements MusicContract.MusicView, View.OnClickListener {

    private static final int MODE_LIST_LOOP = 0;
    private static final int MODE_SINGER_LOOP = 1;
    private static final int MODE_SHUFFLE_PLAYBACK = 3;

    private static final int FRAGMENT_PHOTO = 0XCCCC;
    private static final int FRAGMENT_LYRICS = 0XDDDD;

    private Toolbar mTbTitle;
    private ImageView mIvMode, mIvPlay, mIvList, mIvNext, mIvPast;
    private FrameLayout mFlytCenter;
    private int[] mModeDrawable = {R.drawable.music_list_loop,
            R.drawable.music_single_loop, R.drawable.music_shuffle_playback};
    private LocalMusic mLocalMusic;
    private boolean isPlaying;
    private int currFragment = FRAGMENT_PHOTO;
    private int currMode = MODE_LIST_LOOP;
    private PhotoFragment mPhotoFragment;
    private LyricsFragment mLyricsFragment;

    public static void actionStart(Context context, LocalMusic lm) {
        Intent i = new Intent(context, MusicActivity.class);
        i.putExtra("local_music", lm);
        context.startActivity(i);
    }

    @Override
    protected MusicPresenterImp createPresenter() {
        return new MusicPresenterImp(this);
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
        initToolbar(mTbTitle, R.drawable.back_icon);
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
    }

    private void showList() {

    }

    private void nextSong() {

    }

    private void pastSong() {

    }

    private void processPlayback() {
        if (isPlaying) {
            mIvPlay.setImageResource(R.drawable.music_play);
            isPlaying = false;
        } else {
            mIvPlay.setImageResource(R.drawable.music_playing);
            isPlaying = true;
        }
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void loadData() {

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
    protected void getBottomState(BottomMusic music) {

    }

    private void replaceFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(currFragment == FRAGMENT_PHOTO){
            transaction.replace(R.id.music_flyt_center, mLyricsFragment);
            currFragment = FRAGMENT_LYRICS;
        }
        if(currFragment == FRAGMENT_LYRICS){
            transaction.replace(R.id.music_flyt_center, mPhotoFragment);
            currFragment = FRAGMENT_PHOTO;
        }
        transaction.commit();
    }

    @Override
    protected BottomMusic setBottomState() {
        return null;
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

}
