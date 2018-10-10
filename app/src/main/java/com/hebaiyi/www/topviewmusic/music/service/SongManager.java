package com.hebaiyi.www.topviewmusic.music.service;

import android.annotation.SuppressLint;

import com.hebaiyi.www.topviewmusic.bean.Music;
import com.hebaiyi.www.topviewmusic.util.MatheUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class SongManager {

    public static final int MODE_LIST_LOOP = 0;
    public static final int MODE_SINGER_LOOP = 1;
    public static final int MODE_SHUFFLE_PLAYBACK = 2;
    private List<Integer> mModeList;
    private List<Music> mList;
    private int currPosition;
    private MusicManager mManager;
    private int mMode;

    private SongManager() {
        mManager = MusicManager.getInstance();
        mModeList = new ArrayList<>();
        mModeList.add(MODE_LIST_LOOP);
        mModeList.add(MODE_SINGER_LOOP);
        mModeList.add(MODE_SHUFFLE_PLAYBACK);
    }

    private static class Singleton {
        @SuppressLint("StaticFieldLeak")
        private static SongManager instance = new SongManager();
    }

    public static SongManager getInstance() {
        return Singleton.instance;
    }

    public void setSongList(List<Music> currList) {
        mList = currList;
    }

    public void changeMode(int mode){
        mMode = mode;
    }

    public List<Integer> ObtainModeList(){
        return mModeList;
    }

    public void nextSong(boolean manual) {
        if (mList == null || mList.size() == 0) {
            return;
        }
        switch (mMode) {
            case MODE_LIST_LOOP:
                listLoop();
                break;
            case MODE_SINGER_LOOP:
                if (manual) {
                    listLoop();
                } else {
                    singleLoop();
                }
                break;
            case MODE_SHUFFLE_PLAYBACK:
                shufflePlayback();
                break;
            default:
                listLoop();
                break;
        }
    }

    public void pastSong() {
        if (mList == null || mList.size() == 0) {
            return;
        }
        if (currPosition - 1 < 0) {
            currPosition = mList.size() - 1;
            postMusic(currPosition);
            return;
        }
        postMusic(--currPosition);
    }

    private void postMusic(int position) {
        if (position >= mList.size()) {
            return;
        }
        Music music = mList.get(position);
        mManager.setSong(music.getUrl());
        EventBus.getDefault().postSticky(music);
    }

    private void listLoop() {
        if (currPosition + 1 == mList.size()) {
            currPosition = 0;
            postMusic(currPosition);
            return;
        }
        postMusic(++currPosition);
    }

    private void singleLoop() {
        postMusic(currPosition);
    }

    private void shufflePlayback() {
        currPosition = MatheUtil.createRandomNumber(0, mList.size() - 1);
        postMusic(currPosition);
    }

    public int obtainCurrPosition(){
        return currPosition;
    }

    public void setCurrPosition(int position){
        currPosition = position;
    }

}
