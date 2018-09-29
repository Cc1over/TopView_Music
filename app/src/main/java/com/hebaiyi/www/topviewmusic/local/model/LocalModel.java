package com.hebaiyi.www.topviewmusic.local.model;

import android.support.annotation.NonNull;

import com.hebaiyi.www.topviewmusic.bean.HotGeDan;
import com.hebaiyi.www.topviewmusic.util.HttpUtil;
import com.hebaiyi.www.topviewmusic.util.Utility;

import java.io.IOException;
import java.util.List;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LocalModel {

    public void loadHotGeDan(int num, final HotGeDanCallback callback) {
        String address = MusicApi.GeDan.hotGeDan(num);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                String json = Utility.obtainDesignationJson(data, "content.list");
                callback.onSuccess(Utility.analyzeHotGeDan(json));
            }
        });
    }

    /**
     * 请求热门歌单列表的回调接口
     */
    public interface HotGeDanCallback {

        void onSuccess(List<HotGeDan> hotGeDans);

        void onFail(Exception e);

    }

}
