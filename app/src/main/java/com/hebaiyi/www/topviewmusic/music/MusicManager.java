package com.hebaiyi.www.topviewmusic.music;

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
import com.hebaiyi.www.topviewmusic.music.service.MusicService;

public class MusicManager {

    private IMusicManager mManager;
    private boolean isConnected = false;
    private MusicReceiver mReceiver;
    private Context context = TopViewMusicApplication.getContext();
    private OnMusicCompleteListener mListener;

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

    private void registered(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicService.MUSIC_BROADCAST_RECEIVER_ACTION);
        mReceiver = new MusicReceiver();
        context.registerReceiver(mReceiver, filter);
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

    public void setSong(String songUrl) {
        if(songUrl.isEmpty()){
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

    private class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mListener != null) {
                mListener.onComplete();
            }
        }

    }

    public interface OnMusicCompleteListener {
        void onComplete();
    }

    public void setOnMusicCompleteListener(OnMusicCompleteListener oml) {
        mListener = oml;
    }
}
