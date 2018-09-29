package com.hebaiyi.www.topviewmusic.util;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    private static OkHttpClient sClient = new OkHttpClient();

    public static void asyncRequest(final String address, final Callback callback) {
        Request request = new Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/60.0.3112.113 Safari/537.36")
                .url(address)
                .build();
        sClient.newCall(request).enqueue(callback);

    }

    public static String syncRequest(String address) {
        try {
            Request request = new Request.Builder()
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/60.0.3112.113 Safari/537.36")
                    .url(address)
                    .build();
            Response response = sClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
