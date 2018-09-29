package com.hebaiyi.www.topviewmusic.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.hebaiyi.www.topviewmusic.music.IMusicManager;

import java.io.IOException;

public class MusicService extends Service {

    private IMusicManager.Stub mManager;
    private MediaPlayer mPlayer;
    private boolean needToReset = false;
    private String currSongUrl;
//    public static final String

    public MusicService() {
        mPlayer = new MediaPlayer();
        mManager = new IMusicManager.Stub() {
            @Override
            public void setSong(String songUrl) throws RemoteException {
                Log.e("setSong: ", songUrl);
                if (currSongUrl == null) {
                    currSongUrl = songUrl;
                } else {
                    if(currSongUrl.equals(songUrl)){
                        return;
                    }
                    currSongUrl = songUrl;
                }
                try {
                    if (!needToReset) {
                        mPlayer.setDataSource(songUrl);
                        mPlayer.prepare();
                        needToReset = true;
                    } else {
                        mPlayer.reset();
                        mPlayer.setDataSource(songUrl);
                        mPlayer.prepare();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void start() throws RemoteException {
                Log.e("start: ", "start()");
                if (!mPlayer.isPlaying()) {
                    mPlayer.start();
                }
            }

            @Override
            public void pause() throws RemoteException {
                Log.e("pause: ", "pause()");
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
            }

            @Override
            public void stop() throws RemoteException {
                Log.e("stop: ", "stop()");
            }

            @Override
            public void setCurrTime(int currTime) throws RemoteException {
                Log.e("setCurrTime: ", currTime + "");
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mManager;
    }


}
