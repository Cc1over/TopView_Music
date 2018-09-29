package com.hebaiyi.www.topviewmusic.bean;

import com.google.gson.annotations.SerializedName;

public class Channel {

    @SerializedName("name")
    private String name;

    @SerializedName("channelid")
    private int channelId;

    @SerializedName("thumb")
    private String picture;

    @SerializedName("ch_name")
    private String channelName;

    @SerializedName("value")
    private int value;

    @SerializedName("cate_name")
    private String cateName;

    @SerializedName("cate_sname")
    private String cateSname;

    @SerializedName("listen_num")
    private int listenNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateSname() {
        return cateSname;
    }

    public void setCateSname(String cateSname) {
        this.cateSname = cateSname;
    }

    public int getListenNum() {
        return listenNum;
    }

    public void setListenNum(int listenNum) {
        this.listenNum = listenNum;
    }
}
