package com.hebaiyi.www.topviewmusic.bean;

import com.google.gson.annotations.SerializedName;

public class SearchMerge {

    @SerializedName("tag_info")
    private String tagInfo;

    @SerializedName("video_info")
    private String videoInfo;

    @SerializedName("syn_words")
    private String synWords;

    @SerializedName("query")
    private String query;

    @SerializedName("user_info")
    private String userInfo;

    @SerializedName("album_info")
    private String albumInfo;

    @SerializedName("rqt_type")
    private int rqtType;

    @SerializedName("song_info")
    private String songInfo;

    @SerializedName("playlist_info")
    private String playlistInfo;

    @SerializedName("artist_info")
    private String artistInfo;

    public String getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(String tagInfo) {
        this.tagInfo = tagInfo;
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getSynWords() {
        return synWords;
    }

    public void setSynWords(String synWords) {
        this.synWords = synWords;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getAlbumInfo() {
        return albumInfo;
    }

    public void setAlbumInfo(String albumInfo) {
        this.albumInfo = albumInfo;
    }

    public int getRqtType() {
        return rqtType;
    }

    public void setRqtType(int rqtType) {
        this.rqtType = rqtType;
    }

    public String getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(String songInfo) {
        this.songInfo = songInfo;
    }

    public String getPlaylistInfo() {
        return playlistInfo;
    }

    public void setPlaylistInfo(String playlistInfo) {
        this.playlistInfo = playlistInfo;
    }

    public String getArtistInfo() {
        return artistInfo;
    }

    public void setArtistInfo(String artistInfo) {
        this.artistInfo = artistInfo;
    }


    public class ArtistInfo{

        @SerializedName("ting_uid")
        private String tingUid;

        @SerializedName("song_num")
        private int songNum;

        @SerializedName("country")
        private String country;

        @SerializedName("avatar_middle")
        private String avatarMiddle;

        @SerializedName("album_num")
        private int albumNum;

        @SerializedName("artist_desc")
        private String artistDesc;

        @SerializedName("author")
        private String author;

        @SerializedName("artist_source")
        private String artistSource;

        @SerializedName("artist_id")
        private String artistId;

        public String getTingUid() {
            return tingUid;
        }

        public void setTingUid(String tingUid) {
            this.tingUid = tingUid;
        }

        public int getSongNum() {
            return songNum;
        }

        public void setSongNum(int songNum) {
            this.songNum = songNum;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAvatarMiddle() {
            return avatarMiddle;
        }

        public void setAvatarMiddle(String avatarMiddle) {
            this.avatarMiddle = avatarMiddle;
        }

        public int getAlbumNum() {
            return albumNum;
        }

        public void setAlbumNum(int albumNum) {
            this.albumNum = albumNum;
        }

        public String getArtistDesc() {
            return artistDesc;
        }

        public void setArtistDesc(String artistDesc) {
            this.artistDesc = artistDesc;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getArtistSource() {
            return artistSource;
        }

        public void setArtistSource(String artistSource) {
            this.artistSource = artistSource;
        }

        public String getArtistId() {
            return artistId;
        }

        public void setArtistId(String artistId) {
            this.artistId = artistId;
        }
    }

    public class SongInfo{

        @SerializedName("resource_type_ext")
        private String resourceTypeExt;

        @SerializedName("has_filmtv")
        private String hasFilmtv;

        @SerializedName("resource_type")
        private String resourceType;

        @SerializedName("mv_provider")
        private String mvProvider;

        @SerializedName("del_status")
        private String delStatus;

        @SerializedName("havehigh")
        private String haveHigh;

        @SerializedName("si_proxycompany")
        private String siProxyCompany;

        @SerializedName("versions")
        private String versions;

        @SerializedName("toneid")
        private String toneid;

        @SerializedName("info")
        private String info;

        @SerializedName("has_mv")
        private String hasMv;

        @SerializedName("album_title")
        private String albumTitle;

        @SerializedName("content")
        private String content;

        @SerializedName("piao_id")
        private String piaoId;

        @SerializedName("artist_id")
        private String artistId;

        @SerializedName("lrclink")
        private String lrClink;

        @SerializedName("title")
        private String title;

        @SerializedName("author")
        private String author;

        @SerializedName("song_id")
        private String songId;

        @SerializedName("all_artist_id")
        private String allArtistId;

        @SerializedName("ting_id")
        private String tingUid;

        @SerializedName("pic_small")
        private String picSmall;

        public String getPicSmall() {
            return picSmall;
        }

        public void setPicSmall(String picSmall) {
            this.picSmall = picSmall;
        }

        public String getResourceTypeExt() {
            return resourceTypeExt;
        }

        public void setResourceTypeExt(String resourceTypeExt) {
            this.resourceTypeExt = resourceTypeExt;
        }

        public String getHasFilmtv() {
            return hasFilmtv;
        }

        public void setHasFilmtv(String hasFilmtv) {
            this.hasFilmtv = hasFilmtv;
        }

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public String getMvProvider() {
            return mvProvider;
        }

        public void setMvProvider(String mvProvider) {
            this.mvProvider = mvProvider;
        }

        public String getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(String delStatus) {
            this.delStatus = delStatus;
        }

        public String getHaveHigh() {
            return haveHigh;
        }

        public void setHaveHigh(String haveHigh) {
            this.haveHigh = haveHigh;
        }

        public String getSiProxyCompany() {
            return siProxyCompany;
        }

        public void setSiProxyCompany(String siProxyCompany) {
            this.siProxyCompany = siProxyCompany;
        }

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getHasMv() {
            return hasMv;
        }

        public void setHasMv(String hasMv) {
            this.hasMv = hasMv;
        }

        public String getAlbumTitle() {
            return albumTitle;
        }

        public void setAlbumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPiaoId() {
            return piaoId;
        }

        public void setPiaoId(String piaoId) {
            this.piaoId = piaoId;
        }

        public String getArtistId() {
            return artistId;
        }

        public void setArtistId(String artistId) {
            this.artistId = artistId;
        }

        public String getLrClink() {
            return lrClink;
        }

        public void setLrClink(String lrClink) {
            this.lrClink = lrClink;
        }

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

        public String getAllArtistId() {
            return allArtistId;
        }

        public void setAllArtistId(String allArtistId) {
            this.allArtistId = allArtistId;
        }

        public String getTingUid() {
            return tingUid;
        }

        public void setTingUid(String tingUid) {
            this.tingUid = tingUid;
        }
    }

    public class AlbumInfo{

        @SerializedName("all_aritist_id")
        private int allAritistId;

        @SerializedName("publishtime")
        private String publishtime;

        @SerializedName("company")
        private String company;

        @SerializedName("album_desc")
        private String albumDesc;

        @SerializedName("title")
        private String title;

        @SerializedName("album_id")
        private int albumId;

        @SerializedName("pic_small")
        private String picSmall;

        @SerializedName("hot")
        private int hot;

        @SerializedName("author")
        private String author;

        @SerializedName("artist_id")
        private int artistId;

        public int getAllAritistId() {
            return allAritistId;
        }

        public void setAllAritistId(int allAritistId) {
            this.allAritistId = allAritistId;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAlbumDesc() {
            return albumDesc;
        }

        public void setAlbumDesc(String albumDesc) {
            this.albumDesc = albumDesc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAlbumId() {
            return albumId;
        }

        public void setAlbumId(int albumId) {
            this.albumId = albumId;
        }

        public String getPicSmall() {
            return picSmall;
        }

        public void setPicSmall(String picSmall) {
            this.picSmall = picSmall;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getArtistId() {
            return artistId;
        }

        public void setArtistId(int artistId) {
            this.artistId = artistId;
        }
    }

}



