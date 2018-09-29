package com.hebaiyi.www.topviewmusic.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Billboard {

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private int type;

    @SerializedName("count")
    private int count;

    @SerializedName("comment")
    private String comment;

    @SerializedName("pic_s192")
    private String picS192;

    @SerializedName("pic_s444")
    private String picS444;

    @SerializedName("pic_s260")
    private String picS260;

    @SerializedName("pic_s210")
    private String picS210;

    @SerializedName("content")
    private List<Content> contents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPicS192() {
        return picS192;
    }

    public void setPicS192(String picS192) {
        this.picS192 = picS192;
    }

    public String getPicS444() {
        return picS444;
    }

    public void setPicS444(String picS444) {
        this.picS444 = picS444;
    }

    public String getPicS260() {
        return picS260;
    }

    public void setPicS260(String picS260) {
        this.picS260 = picS260;
    }

    public String getPicS210() {
        return picS210;
    }

    public void setPicS210(String picS210) {
        this.picS210 = picS210;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}