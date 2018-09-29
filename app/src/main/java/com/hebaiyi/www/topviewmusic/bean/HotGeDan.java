package com.hebaiyi.www.topviewmusic.bean;

import com.google.gson.annotations.SerializedName;

public class HotGeDan {

    @SerializedName("listid")
    private int listId;

    @SerializedName("pic")
    private String picture;

    @SerializedName("listnum")
    private int listNum;

    @SerializedName("collectnum")
    private int collectNum;

    @SerializedName("title")
    private String title;

    @SerializedName("tag")
    private String tag;

    @SerializedName("type")
    private String type;

    public int getListNum() {
        return listNum;
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
