package com.hebaiyi.www.topviewmusic.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class TopViewMusicApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext(){
        return sContext;
    }


}
