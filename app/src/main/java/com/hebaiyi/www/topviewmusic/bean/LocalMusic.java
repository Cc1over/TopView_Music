package com.hebaiyi.www.topviewmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LocalMusic implements Parcelable {

    private long id;
    private int albumId;
    private String title;
    private String artist;
    private long size;
    private String url;
    private long duration;
    private String album;
    private String albumPic;

    public LocalMusic(){

    }

    protected LocalMusic(Parcel in) {
        id = in.readLong();
        albumId = in.readInt();
        title = in.readString();
        artist = in.readString();
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

    public String getAlbumPic() {
        return albumPic;
    }

    public void setAlbumPic(String albumPic) {
        this.albumPic = albumPic;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(albumId);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeLong(size);
        dest.writeString(url);
        dest.writeLong(duration);
        dest.writeString(album);
        dest.writeString(albumPic);
    }
}
