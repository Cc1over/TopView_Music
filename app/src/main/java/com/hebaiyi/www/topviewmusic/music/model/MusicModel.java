package com.hebaiyi.www.topviewmusic.music.model;

import android.support.annotation.NonNull;

import com.hebaiyi.www.topviewmusic.util.HttpUtil;

import java.io.IOException;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MusicModel {

    public void loadNetMusic(String songId){
        String address = MusicApi.Song.songInfo(songId);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }

    public interface MusicModelCallback{

        void onSuccess();

        void onFail();

    }



}
