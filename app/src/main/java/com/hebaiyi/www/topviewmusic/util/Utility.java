package com.hebaiyi.www.topviewmusic.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebaiyi.www.topviewmusic.bean.Billboard;
import com.hebaiyi.www.topviewmusic.bean.Channel;
import com.hebaiyi.www.topviewmusic.bean.FoucsPic;
import com.hebaiyi.www.topviewmusic.bean.GeDan;
import com.hebaiyi.www.topviewmusic.bean.HotGeDan;
import com.hebaiyi.www.topviewmusic.bean.HotWord;
import com.hebaiyi.www.topviewmusic.bean.NetMusic;
import com.hebaiyi.www.topviewmusic.bean.RecommendRadio;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;
import com.hebaiyi.www.topviewmusic.bean.Song;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class Utility {

    public static List<RecommendRadio> analyzeRecommendRadio(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<RecommendRadio>>() {
        }.getType());
    }

    public static NetMusic analyzeNetMusic(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, NetMusic.class);
    }

    public static List<HotWord> analyzeHotWord(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<HotWord>>() {
        }.getType());
    }

    public static List<SearchMerge.AlbumInfo> analyzeSearchMergeAlbumInfo(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<SearchMerge.AlbumInfo>>() {
        }.getType());
    }

    public static List<SearchMerge.ArtistInfo> analyzeSearchMergeArtistInfo(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<SearchMerge.ArtistInfo>>() {
        }.getType());
    }

    public static List<SearchMerge.SongInfo> analyzeSearchMergeSongInfo(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<SearchMerge.SongInfo>>() {
        }.getType());
    }

    public static List<Billboard> analyzeBillboard(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Billboard>>() {
        }.getType());
    }

    public static List<Channel> analyzeChannel(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Channel>>() {
        }.getType());
    }

    public static List<HotGeDan> analyzeHotGeDan(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<HotGeDan>>() {
        }.getType());
    }

    public static List<GeDan> analyzeGeDan(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<GeDan>>() {
        }.getType());
    }

    public static List<FoucsPic> analyzeFoucsPic(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<FoucsPic>>() {
        }.getType());
    }

    public static List<Song> analyzeSong(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Song>>() {
        }.getType());
    }

    /**
     * 在json多层嵌套情况下，获取指定路径的json字符串，
     * 如：a.b.c.d.e.f，获取到f层级的json字符串
     *
     * @param json  未加处理的json字符串
     * @param route json的指定路径
     * @return 处理过后的json字符串
     */
    public static String obtainDesignationJson(String json, String route) {
        if (json.isEmpty()) {
            return null;
        }
        // 获取到每一级json字符串的key
        String[] keys = route.split("\\.");
        String newJson = json;
        for (int i = 0; i < keys.length; i++) {
            newJson = splitJsonByKey(newJson, keys[i]);
            if (i == keys.length - 1) {
                return newJson;
            }
        }
        return null;
    }

    /**
     * 根据json字符串的路径裁截子json字符串
     *
     * @param json 原json字符串
     * @param key  json字符串中的键
     * @return 裁截处理后的子json字符串
     */
    private static String splitJsonByKey(String json, String key) {
        try {
            JSONObject obj = new JSONObject(json);
            for (Iterator<String> it = obj.keys(); it.hasNext(); ) {
                String s = it.next();
                if (s == null) {
                    return null;
                }
                if (s.equals(key)) {
                    return obj.getString(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


}
