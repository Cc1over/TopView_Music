package com.hebaiyi.www.topviewmusic.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeDan {

    @SerializedName("listid")
    private int listId;

    @SerializedName("listnum")
    private int listnum;

    @SerializedName("title")
    private String title;

    @SerializedName("collectnum")
    private int collectNum;

    @SerializedName("pic_300")
    private String pic300;

    @SerializedName("tag")
    private String tag;

    @SerializedName("desc")
    private String desc;

    @SerializedName("pic_w300")
    private String picW300;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("songIds")
    private List<Integer> songIds;


    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getListnum() {
        return listnum;
    }

    public void setListnum(int listnum) {
        this.listnum = listnum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public String getPic300() {
        return pic300;
    }

    public void setPic300(String pic300) {
        this.pic300 = pic300;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicW300() {
        return picW300;
    }

    public void setPicW300(String picW300) {
        this.picW300 = picW300;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Integer> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Integer> songIds) {
        this.songIds = songIds;
    }
}
