package com.hebaiyi.www.topviewmusic.bean;

import com.google.gson.annotations.SerializedName;

public class RecommendRadio {

    @SerializedName("channelid")
    private int channelId;

    @SerializedName("itemid")
    private int itemId;

    @SerializedName("album_id")
    private int albumId;

    @SerializedName("title")
    private String title;

    @SerializedName("pic")
    private String picture;

    @SerializedName("type")
    private String type;

    @SerializedName("desc")
    private String desc;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
