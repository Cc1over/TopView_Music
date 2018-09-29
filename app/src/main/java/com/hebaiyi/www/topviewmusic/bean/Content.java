package com.hebaiyi.www.topviewmusic.bean;

import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("song_id")
    private String songId;

    @SerializedName("album_id")
    private String albumId;

    @SerializedName("album_title")
    private String albumTitle;

    @SerializedName("rank_change")
    private int rankChange;

    @SerializedName("all_rate")
    private String allRate;

    @SerializedName("biaoshi")
    private String biaoshi;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public int getRankChange() {
        return rankChange;
    }

    public void setRankChange(int rankChange) {
        this.rankChange = rankChange;
    }

    public String getAllRate() {
        return allRate;
    }

    public void setAllRate(String allRate) {
        this.allRate = allRate;
    }

    public String getBiaoshi() {
        return biaoshi;
    }

    public void setBiaoshi(String biaoshi) {
        this.biaoshi = biaoshi;
    }
}
