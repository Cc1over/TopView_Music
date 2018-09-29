package com.hebaiyi.www.topviewmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HotWord implements Parcelable {

    @SerializedName("strong")
    private int strong;

    @SerializedName("word")
    private String word;

    @SerializedName("linktype")
    private int linkType;

    @SerializedName("linkurl")
    private String linkUrl;


    protected HotWord(Parcel in) {
        strong = in.readInt();
        word = in.readString();
        linkType = in.readInt();
        linkUrl = in.readString();
    }

    public static final Creator<HotWord> CREATOR = new Creator<HotWord>() {
        @Override
        public HotWord createFromParcel(Parcel in) {
            return new HotWord(in);
        }

        @Override
        public HotWord[] newArray(int size) {
            return new HotWord[size];
        }
    };

    public int getStrong() {
        return strong;
    }

    public void setStrong(int strong) {
        this.strong = strong;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(strong);
        dest.writeString(word);
        dest.writeInt(linkType);
        dest.writeString(linkUrl);
    }
}
