package com.hebaiyi.www.topviewmusic.search.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hebaiyi.www.topviewmusic.bean.HotWord;
import com.hebaiyi.www.topviewmusic.bean.Music;
import com.hebaiyi.www.topviewmusic.bean.NetMusic;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.util.HttpUtil;
import com.hebaiyi.www.topviewmusic.util.Utility;

import java.io.IOException;
import java.util.List;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchModel {

    public void loadSearchMerge(String query, int pageNo,
                                int pageSize, final SearchMergeCallback callback) {

        String address = MusicApi.Search.searchMerge(query, pageNo, pageSize);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                String sais = Utility
                        .obtainDesignationJson(data, "result.album_info.album_list");
                String sars = Utility
                        .obtainDesignationJson(data, "result.artist_info.artist_list");
                String ssis = Utility
                        .obtainDesignationJson(data, "result.song_info.song_list");
                List<SearchMerge.AlbumInfo> ais = Utility.analyzeSearchMergeAlbumInfo(sais);
                List<SearchMerge.ArtistInfo> ars = Utility.analyzeSearchMergeArtistInfo(sars);
                List<SearchMerge.SongInfo> sis = Utility.analyzeSearchMergeSongInfo(ssis);
                callback.onSuccess(ais, ars, sis);
            }
        });
    }

    public void loadNetMusic(String songId, final NetMusicCallback callback) {
        String address = MusicApi.Song.songInfo(songId);
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                callback.onSuccess();
            }
        });
    }

    public void loadHotWord(final HotWordCallback callback) {
        String address = MusicApi.Search.hotWord();
        HttpUtil.asyncRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                String data = response.body().string();
                String json = Utility.obtainDesignationJson(data, "result");
                callback.onSuccess(Utility.analyzeHotWord(json));
            }
        });
    }

    public interface HotWordCallback {

        void onSuccess(List<HotWord> hotWords);

        void onFail(Exception e);

    }

    public interface SearchMergeCallback {

        void onSuccess(List<SearchMerge.AlbumInfo> ais,
                       List<SearchMerge.ArtistInfo> ars,
                       List<SearchMerge.SongInfo> sis);

        void onFail(Exception e);
    }

    public interface NetMusicCallback {

        void onSuccess(NetMusic nm);

        void onFail(Exception e);

    }


}
