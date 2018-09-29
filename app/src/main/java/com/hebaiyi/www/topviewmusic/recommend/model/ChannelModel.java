package com.hebaiyi.www.topviewmusic.recommend.model;

import android.support.annotation.NonNull;

import com.hebaiyi.www.topviewmusic.bean.Channel;
import com.hebaiyi.www.topviewmusic.util.HttpUtil;
import com.hebaiyi.www.topviewmusic.util.Utility;

import java.io.IOException;
import java.util.List;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChannelModel {

    public void loadChannel(int pageNo, int pageSize, final ChannelCallback callback){
        String address = MusicApi.Radio.recChannel(pageNo,pageSize);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                 String data = response.body().string();
                 String json = Utility.obtainDesignationJson(data,"result.list");
                 callback.onSuccess(Utility.analyzeChannel(json));
            }
        });
    }


    public interface ChannelCallback{

        void onFail(Exception e);

        void onSuccess(List<Channel> channels);

    }


}
