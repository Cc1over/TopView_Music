package com.hebaiyi.www.topviewmusic.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.hebaiyi.www.topviewmusic.app.TopViewMusicApplication;

public class ToastUtil {

    private static Toast mToast;
    @SuppressLint("StaticFieldLeak")
    private static Context context = TopViewMusicApplication.getContext();

    @SuppressLint("ShowToast")
    public static void showToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }

        mToast.show();
    }
}
