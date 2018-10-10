package com.hebaiyi.www.topviewmusic.music.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.hebaiyi.www.topviewmusic.R;
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
    private RemoteViews contentView;

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
                                Intent i = new Intent(MUSIC_BROADCAST_RECEIVER_PREPARED_ACTION);
                                sendBroadcast(i);
                                showNotification();
                                mPlayer.start();
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
                mPlayer.seekTo(currTime);
            }

            @Override
            public int getDuration() throws RemoteException {
                return mPlayer.getDuration();
            }

            @Override
            public float getProgress() throws RemoteException {
                if (mPlayer.isPlaying()) {
                    return mPlayer.getCurrentPosition() * 100f / mPlayer.getDuration();
                }
                return -1;
            }
        };
    }

    private void showNotification() {
        String CHANNEL_ID = "1";
        String CHANNEL_NAME = "channel_name";
        NotificationManager manager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_MAX);
            manager.createNotificationChannel(channel);
        }
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.layout_notification_control);
//        createPendingIntent();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContent(remoteViews)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.c1over_icon)
                .build();
        startForeground(1, notification);
    }

    private void createPendingIntent(){
        Intent intentPlay = new Intent("play");
        PendingIntent pIntentPlay = PendingIntent.getBroadcast(this, 0,
                intentPlay, 0);
        contentView.setOnClickPendingIntent(R.id.control_ib_play, pIntentPlay);
        Intent intentNext = new Intent("next");
        PendingIntent pIntentNext = PendingIntent.getBroadcast(this, 0,
                intentNext, 0);
        contentView.setOnClickPendingIntent(R.id.control_ib_next, pIntentNext);
        Intent intentPast = new Intent("past");
        PendingIntent pIntentLast = PendingIntent.getBroadcast(this, 0,
                intentPast, 0);
        contentView.setOnClickPendingIntent(R.id.control_ib_past, pIntentLast);
        Intent intentClose = new Intent("close");
        PendingIntent pIntentCancel = PendingIntent.getBroadcast(this, 0,
                intentClose, 0);
        contentView.setOnClickPendingIntent(R.id.control_ib_close, pIntentCancel);
    }



}
