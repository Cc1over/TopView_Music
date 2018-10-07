package com.hebaiyi.www.topviewmusic.music.model;

import android.support.annotation.NonNull;

import com.hebaiyi.www.topviewmusic.util.HttpUtil;

import java.io.IOException;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicModel {

    public void loadLyrics(String lrc, final LyricsCallback callback) {
        HttpUtil.asyncRequest(lrc, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
            }
        });

    }

    public interface LyricsCallback {

        void onSuccess();

        void onFail();

    }


}
