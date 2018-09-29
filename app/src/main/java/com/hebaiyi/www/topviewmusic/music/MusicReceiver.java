package com.hebaiyi.www.topviewmusic.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hebaiyi.www.topviewmusic.music.service.MusicService;

public class MusicReceiver extends BroadcastReceiver {

    private OnMusicCompleteListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mListener != null) {
            mListener.onComplete();
        }
    }

    public interface OnMusicCompleteListener {
        void onComplete();
    }

    public void setOnMusicCompleteListener(OnMusicCompleteListener oml) {
        mListener = oml;
    }

}
