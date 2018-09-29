package com.hebaiyi.www.topviewmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BottomMusic implements Parcelable {

    private String picUrl;

    private String name;

    private String singer;

    private boolean isPlaying;

    private String playUrl;

    public BottomMusic(){

    }

    public BottomMusic(String picUrl, String name, String singer, boolean isPlaying,String playUrl) {
        this.picUrl = picUrl;
        this.name = name;
        this.singer = singer;
        this.isPlaying = isPlaying;
        this.playUrl = playUrl;
    }


    protected BottomMusic(Parcel in) {
        picUrl = in.readString();
        name = in.readString();
        singer = in.readString();
        isPlaying = in.readByte() != 0;
        playUrl = in.readString();
    }

    public static final Creator<BottomMusic> CREATOR = new Creator<BottomMusic>() {
        @Override
        public BottomMusic createFromParcel(Parcel in) {
            return new BottomMusic(in);
        }

        @Override
        public BottomMusic[] newArray(int size) {
            return new BottomMusic[size];
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

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
    public String getSinger() {
        return singer;
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
        dest.writeString(playUrl);
    }

}
