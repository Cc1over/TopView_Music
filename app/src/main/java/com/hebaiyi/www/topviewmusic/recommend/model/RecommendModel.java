package com.hebaiyi.www.topviewmusic.recommend.model;

import android.support.annotation.NonNull;

import com.hebaiyi.www.topviewmusic.bean.FoucsPic;
import com.hebaiyi.www.topviewmusic.bean.GeDan;
import com.hebaiyi.www.topviewmusic.bean.RecommendRadio;
import com.hebaiyi.www.topviewmusic.bean.Song;
import com.hebaiyi.www.topviewmusic.util.HttpUtil;
import com.hebaiyi.www.topviewmusic.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecommendModel {

    public void loadFoucsPic(int num, final FoucsPicCallback callback) {
        String address = MusicApi.focusPic(num);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                String json = Utility.obtainDesignationJson(data, "pic");
                callback.onSuccess(Utility.analyzeFoucsPic(json));
            }
        });
    }


    public void loadRecommendSongList(int num, final RecommendSongCallback callback) {
        String address = MusicApi.Song.recommendSong(num);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject obj = new JSONObject(data);
                    JSONArray content = obj.getJSONArray("content");
                    JSONObject object = content.getJSONObject(0);
                    String json = object.getString("song_list");
                    callback.onSuccess(Utility.analyzeSong(json));
                } catch (JSONException e) {
                    callback.onFail(e);
                }
            }
        });
    }


    public void loadGeDan(int num,int pageNo,final GeDanCallback callback){
        String address = MusicApi.GeDan.geDan(pageNo,num);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                String json = Utility.obtainDesignationJson(data,"content");
                callback.onSuccess(Utility.analyzeGeDan(json));
            }
        });
    }

    public void loadRecommendRadio(int num, final RecommendRadioCallback callback){
        String address = MusicApi.Radio.recommendRadioList(num);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                String json = Utility.obtainDesignationJson(data,"list");
                callback.onSuccess(Utility.analyzeRecommendRadio(json));
            }
        });
    }

    public interface RecommendRadioCallback{

        void onFail(Exception e);

        void onSuccess(List<RecommendRadio> radios);

    }

    public interface RecommendSongCallback{

        void onFail(Exception e);

        void onSuccess(List<Song> songs);

    }

    public interface GeDanCallback{

        void onSuccess(List<GeDan> geDans);

        void onFail(Exception e);

    }


    public interface FoucsPicCallback {

        void onFail(Exception e);

        void onSuccess(List<FoucsPic> pics);

    }


}
