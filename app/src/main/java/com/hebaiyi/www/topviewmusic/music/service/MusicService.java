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

    public static final String MUSIC_BROADCAST_RECEIVER_COMPLETE_ACTION
            = "TopViewMusic.music.service.MusicService.complete";
    public static final String MUSIC_BROADCAST_RECEIVER_PREPARED_ACTION
            = "TopViewMusic.music.service.MusicService.prepared";
    private IMusicManager.Stub mManager;
    private MediaPlayer mPlayer;
    private boolean needToReset = false;
    private String currSongUrl;

    public MusicService() {
        initMediaPlayer();
        initManager();
    }

    private void initMediaPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent i = new Intent(MUSIC_BROADCAST_RECEIVER_COMPLETE_ACTION);
                sendBroadcast(i);
            }
        });
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

    private void initManager() {
        mManager = new IMusicManager.Stub() {
            @Override
            public void setSong(String songUrl) throws RemoteException {
                if (currSongUrl == null) {
                    currSongUrl = songUrl;
                } else {
                    if (currSongUrl.equals(songUrl)) {
                        return;
                    }
                    currSongUrl = songUrl;
                }
                try {
                    if (!needToReset) {
                        mPlayer.setDataSource(songUrl);
                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mPlayer.start();
                                Intent i = new Intent(MUSIC_BROADCAST_RECEIVER_PREPARED_ACTION);
                                sendBroadcast(i);
                            }
                        });
                        mPlayer.prepareAsync();
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

            @Override
            public int getCurrentPosition() throws RemoteException {
                if (mPlayer.isPlaying()) {
                    return mPlayer.getCurrentPosition();
                }
                return -1;
            }
        };
    }


}
