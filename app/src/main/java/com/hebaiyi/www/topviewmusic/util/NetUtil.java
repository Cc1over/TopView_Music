package com.hebaiyi.www.topviewmusic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

    public static boolean isNetWorkConnection(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

}
