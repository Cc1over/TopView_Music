package com.hebaiyi.www.topviewmusic.recommend.model;

import android.support.annotation.NonNull;

import com.hebaiyi.www.topviewmusic.bean.Billboard;
import com.hebaiyi.www.topviewmusic.util.HttpUtil;
import com.hebaiyi.www.topviewmusic.util.Utility;

import java.io.IOException;
import java.util.List;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BillboardModel {

    public void loadBillboard(final BillboardCallback callback) {
        String address = MusicApi.Billboard.billCategory();
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                 String data = response.body().string();
                 String json = Utility.obtainDesignationJson(data,"content");
                 callback.onSuccess(Utility.analyzeBillboard(json));
            }
        });
    }

    public interface BillboardCallback {

        void onFail(Exception e);

        void onSuccess(List<Billboard> billboards);

    }


}
