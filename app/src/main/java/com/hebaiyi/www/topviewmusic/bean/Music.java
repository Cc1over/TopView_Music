package com.hebaiyi.www.topviewmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hebaiyi.www.topviewmusic.music.contract.MusicContract;

import java.util.List;

public class Music implements Parcelable {

    private String picUrl;
    private String name;
    private String singer;
    private boolean isPlaying;
    private String url;
    private String lyrics;
    private List<String> urls;
    private int duration;

    public Music() {

    }


    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }


    protected Music(Parcel in) {
        picUrl = in.readString();
        name = in.readString();
        singer = in.readString();
        isPlaying = in.readByte() != 0;
        url = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSinger() {
        return singer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(picUrl);
        dest.writeString(name);
        dest.writeString(singer);
        dest.writeByte((byte) (isPlaying ? 1 : 0));
        dest.writeString(url);
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }


}
