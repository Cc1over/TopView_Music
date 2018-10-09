package com.hebaiyi.www.topviewmusic.music.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.hebaiyi.www.topviewmusic.app.TopViewMusicApplication;
import com.hebaiyi.www.topviewmusic.music.IMusicManager;

import java.util.ArrayList;
import java.util.List;

public class MusicManager {

    private IMusicManager mManager;
    private boolean isConnected = false;
    private CompleteReceiver mCompleteReceiver;
    private PrepareReceiver mPrepareReceiver;
    private Context context = TopViewMusicApplication.getContext();
    private List<MusicObserver> mMusicObservers = new ArrayList<>();

    private static class Singleton {
        @SuppressLint("StaticFieldLeak")
        private static MusicManager instance = new MusicManager();
    }

    public static MusicManager getInstance() {
        return Singleton.instance;
    }

    private MusicManager() {
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mManager = IMusicManager.Stub.asInterface(service);
                isConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
            }
        };
        Intent i = new Intent(context, MusicService.class);
        context.bindService(i, connection, Context.BIND_AUTO_CREATE);
        registered();
    }

    private void registered() {
        IntentFilter cfilter = new IntentFilter();
        cfilter.addAction(MusicService.MUSIC_BROADCAST_RECEIVER_COMPLETE_ACTION);
        mCompleteReceiver = new CompleteReceiver();
        context.registerReceiver(mCompleteReceiver, cfilter);
        IntentFilter pfilter = new IntentFilter();
        pfilter.addAction(MusicService.MUSIC_BROADCAST_RECEIVER_PREPARED_ACTION);
        mPrepareReceiver = new PrepareReceiver();
        context.registerReceiver(mPrepareReceiver, pfilter);
    }

    public void start() {
        if (isConnected) {
            try {
                mManager.start();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public float getProgress() {
        if (isConnected) {
            try {
                return mManager.getProgress();
            } catch (RemoteException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    public void stop() {
        if (isConnected) {
            try {
                mManager.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (isConnected) {
            try {
                mManager.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrTime(int currTime) {
        if (isConnected) {
            try {
                mManager.setCurrTime(currTime);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public long getDuration() {
        if (isConnected) {
            try {
                return mManager.getDuration();
            } catch (RemoteException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    public void setSong(String songUrl) {
        if (songUrl == null || "".equals(songUrl)) {
            return;
        }
        if (isConnected) {
            try {
                mManager.setSong(songUrl);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            for (int i = 0; i < mMusicObservers.size(); i++) {
                mMusicObservers.get(i).onComplete();
            }
        }
    }

    private class PrepareReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            for (int i = 0; i < mMusicObservers.size(); i++) {
                mMusicObservers.get(i).OnPrepare();
            }
        }
    }

    public void attach(MusicObserver observer) {
        mMusicObservers.add(observer);
    }

    public void detach(MusicObserver observer) {
        mMusicObservers.remove(observer);
    }

    public interface MusicObserver {

        void OnPrepare();

        void onComplete();
    }


}
