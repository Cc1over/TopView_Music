package com.hebaiyi.www.topviewmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LocalMusic implements Parcelable {

    private long id;
    private int albumId;
    private String name;
    private String singer;
    private long size;
    private String url;
    private long duration;
    private String album;
    private String albumPic;

    public LocalMusic() {

    }


    protected LocalMusic(Parcel in) {
        id = in.readLong();
        albumId = in.readInt();
        name = in.readString();
        singer = in.readString();
        size = in.readLong();
        url = in.readString();
        duration = in.readLong();
        album = in.readString();
        albumPic = in.readString();
    }

    public static final Creator<LocalMusic> CREATOR = new Creator<LocalMusic>() {
        @Override
        public LocalMusic createFromParcel(Parcel in) {
            return new LocalMusic(in);
        }

        @Override
        public LocalMusic[] newArray(int size) {
            return new LocalMusic[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumPic() {
        return albumPic;
    }

    public void setAlbumPic(String albumPic) {
        this.albumPic = albumPic;
    }

    public Music createMusic(boolean isPlaying){
        Music music = new Music();
        music.setPicUrl(albumPic);
        music.setName(name);
        music.setSinger(singer);
        music.setPlaying(isPlaying);
        music.setUrl(url);
        return music;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(albumId);
        dest.writeString(name);
        dest.writeString(singer);
        dest.writeLong(size);
        dest.writeString(url);
        dest.writeLong(duration);
        dest.writeString(album);
        dest.writeString(albumPic);
    }
}
