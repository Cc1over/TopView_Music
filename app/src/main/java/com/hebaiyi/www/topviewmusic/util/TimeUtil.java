package com.hebaiyi.www.topviewmusic.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String conversionToStr(long time) {
        Log.e("conversionToStr", time + "");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        java.util.Date date = new Date(time);
        return sdf.format(date);
    }

}
