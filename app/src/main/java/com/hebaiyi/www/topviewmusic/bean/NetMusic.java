package com.hebaiyi.www.topviewmusic.bean;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NetMusic {

    @SerializedName("songinfo")
    private SongInfo songInfo;

    @SerializedName("songurl")
    private SongUrl songUrl;

    public Music createMusic(boolean isPlaying) {
        Music music = new Music();
        music.setPicUrl(songInfo.picPremium);
        music.setName(songInfo.title);
        music.setSinger(songInfo.artist);
        music.setPlaying(isPlaying);
        music.setLyrics(songInfo.lrclink);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < songUrl.url.size(); i++) {
            urls.add(songUrl.url.get(i).showLink);
        }
        music.setUrls(urls);
        return music;
    }


    public class SongInfo {
        @SerializedName("resource_type_ext")
        private String resourceTypeExt;
        @SerializedName("resource_type")
        private String resourceType;
        @SerializedName("del_status")
        private String delStatus;
        @SerializedName("collect_num")
        private int collectNum;
        private String hot;
        @SerializedName("res_reward_flag")
        private String resRewardFlag;
        @SerializedName("sound_effect")
        private String soundEffect;
        private String title;
        private String language;
        @SerializedName("play_type")
        private int playType;
        private String country;
        private String biaoshi;
        @SerializedName("bitrate_fee")
        private String bitrateFee;
        @SerializedName("song_source")
        private String songSource;
        @SerializedName("is_first_publish")
        private int isFirstPublish;
        @SerializedName("artist_640_1136")
        private String artist6401136;
        @SerializedName("is_secret")
        private String isSecret;
        private int charge;
        @SerializedName("copy_type")
        private String copyType;
        @SerializedName("share_url")
        private String shareUrl;
        @SerializedName("has_mv_mobile")
        private int hasMvMobile;
        @SerializedName("album_no")
        private String albumNo;
        @SerializedName("is_charge")
        private String isCharge;
        @SerializedName("pic_radio")
        private String picRadio;
        @SerializedName("has_filmtv")
        private String hasFilmtv;
        @SerializedName("pic_huge")
        private String picHuge;
        @SerializedName("ting_uid")
        private String tingUid;
        private int expire;
        private String bitrate;
        @SerializedName("si_proxycompany")
        private String siProxycompany;
        private String compose;
        private String toneid;
        private String area;
        private String info;
        @SerializedName("artist_500_500")
        private String artist500500;
        @SerializedName("multiterminal_copytype")
        private String multiterminalCopytype;
        @SerializedName("has_mv")
        private int hasMv;
        @SerializedName("total_listen_nums")
        private String totalListenNums;
        @SerializedName("song_id")
        private String songId;
        @SerializedName("piao_id")
        private String piaoId;
        @SerializedName("high_rate")
        private String highRate;
        @SerializedName("compress_status")
        private String compressStatus;
        private int original;
        @SerializedName("artist_480_800")
        private String artist480800;
        @SerializedName("relate_status")
        private String relateStatus;
        private int learn;
        private String artist;
        @SerializedName("pic_big")
        private String picBig;
        @SerializedName("artist_list")
        private List<ArtistList> artistList;
        @SerializedName("comment_num")
        private int commentNum;
        private String songwriting;
        @SerializedName("pic_singer")
        private String picSinger;
        @SerializedName("album_1000_1000")
        private String album10001000;
        @SerializedName("album_id")
        private String albumId;
        @SerializedName("share_num")
        private int shareNum;
        @SerializedName("album_500_500")
        private String album500500;
        private String aliasname;
        @SerializedName("album_title")
        private String albumTitle;
        @SerializedName("korean_bb_song")
        private String koreanBbSong;
        private String author;
        @SerializedName("all_artist_id")
        private String allArtistId;
        @SerializedName("file_duration")
        private String fileDuration;
        private String publishtime;
        private String versions;
        @SerializedName("artist_1000_1000")
        private String artist10001000;
        @SerializedName("res_encryption_flag")
        private String resEncryptionFlag;
        @SerializedName("all_rate")
        private String allRate;
        @SerializedName("artist_id")
        private String artistId;
        private String distribution;
        private String lrclink;
        @SerializedName("pic_small")
        private String picSmall;
        @SerializedName("original_rate")
        private String originalRate;
        private int havehigh;
        @SerializedName("pic_premium")
        private String picPremium;

        public String getResourceTypeExt() {
            return resourceTypeExt;
        }

        public void setResourceTypeExt(String resourceTypeExt) {
            this.resourceTypeExt = resourceTypeExt;
        }

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public String getDelStatus() {
            return delStatus;
        }

        public void setDelStatus(String delStatus) {
            this.delStatus = delStatus;
        }

        public int getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(int collectNum) {
            this.collectNum = collectNum;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getResRewardFlag() {
            return resRewardFlag;
        }

        public void setResRewardFlag(String resRewardFlag) {
            this.resRewardFlag = resRewardFlag;
        }

        public String getSoundEffect() {
            return soundEffect;
        }

        public void setSoundEffect(String soundEffect) {
            this.soundEffect = soundEffect;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public int getPlayType() {
            return playType;
        }

        public void setPlayType(int playType) {
            this.playType = playType;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getBiaoshi() {
            return biaoshi;
        }

        public void setBiaoshi(String biaoshi) {
            this.biaoshi = biaoshi;
        }

        public String getBitrateFee() {
            return bitrateFee;
        }

        public void setBitrateFee(String bitrateFee) {
            this.bitrateFee = bitrateFee;
        }

        public String getSongSource() {
            return songSource;
        }

        public void setSongSource(String songSource) {
            this.songSource = songSource;
        }

        public int getIsFirstPublish() {
            return isFirstPublish;
        }

        public void setIsFirstPublish(int isFirstPublish) {
            this.isFirstPublish = isFirstPublish;
        }

        public String getArtist6401136() {
            return artist6401136;
        }

        public void setArtist6401136(String artist6401136) {
            this.artist6401136 = artist6401136;
        }

        public String getIsSecret() {
            return isSecret;
        }

        public void setIsSecret(String isSecret) {
            this.isSecret = isSecret;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public String getCopyType() {
            return copyType;
        }

        public void setCopyType(String copyType) {
            this.copyType = copyType;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public int getHasMvMobile() {
            return hasMvMobile;
        }

        public void setHasMvMobile(int hasMvMobile) {
            this.hasMvMobile = hasMvMobile;
        }

        public String getAlbumNo() {
            return albumNo;
        }

        public void setAlbumNo(String albumNo) {
            this.albumNo = albumNo;
        }

        public String getIsCharge() {
            return isCharge;
        }

        public void setIsCharge(String isCharge) {
            this.isCharge = isCharge;
        }

        public String getPicRadio() {
            return picRadio;
        }

        public void setPicRadio(String picRadio) {
            this.picRadio = picRadio;
        }

        public String getHasFilmtv() {
            return hasFilmtv;
        }

        public void setHasFilmtv(String hasFilmtv) {
            this.hasFilmtv = hasFilmtv;
        }

        public String getPicHuge() {
            return picHuge;
        }

        public void setPicHuge(String picHuge) {
            this.picHuge = picHuge;
        }

        public String getTingUid() {
            return tingUid;
        }

        public void setTingUid(String tingUid) {
            this.tingUid = tingUid;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public String getBitrate() {
            return bitrate;
        }

        public void setBitrate(String bitrate) {
            this.bitrate = bitrate;
        }

        public String getSiProxycompany() {
            return siProxycompany;
        }

        public void setSiProxycompany(String siProxycompany) {
            this.siProxycompany = siProxycompany;
        }

        public String getCompose() {
            return compose;
        }

        public void setCompose(String compose) {
            this.compose = compose;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getArtist500500() {
            return artist500500;
        }

        public void setArtist500500(String artist500500) {
            this.artist500500 = artist500500;
        }

        public String getMultiterminalCopytype() {
            return multiterminalCopytype;
        }

        public void setMultiterminalCopytype(String multiterminalCopytype) {
            this.multiterminalCopytype = multiterminalCopytype;
        }

        public int getHasMv() {
            return hasMv;
        }

        public void setHasMv(int hasMv) {
            this.hasMv = hasMv;
        }

        public String getTotalListenNums() {
            return totalListenNums;
        }

        public void setTotalListenNums(String totalListenNums) {
            this.totalListenNums = totalListenNums;
        }

        public String getSongId() {
            return songId;
        }

        public void setSongId(String songId) {
            this.songId = songId;
        }

        public String getPiaoId() {
            return piaoId;
        }

        public void setPiaoId(String piaoId) {
            this.piaoId = piaoId;
        }

        public String getHighRate() {
            return highRate;
        }

        public void setHighRate(String highRate) {
            this.highRate = highRate;
        }

        public String getCompressStatus() {
            return compressStatus;
        }

        public void setCompressStatus(String compressStatus) {
            this.compressStatus = compressStatus;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
        }

        public String getArtist480800() {
            return artist480800;
        }

        public void setArtist480800(String artist480800) {
            this.artist480800 = artist480800;
        }

        public String getRelateStatus() {
            return relateStatus;
        }

        public void setRelateStatus(String relateStatus) {
            this.relateStatus = relateStatus;
        }

        public int getLearn() {
            return learn;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getPicBig() {
            return picBig;
        }

        public void setPicBig(String picBig) {
            this.picBig = picBig;
        }

        public List<ArtistList> getArtistList() {
            return artistList;
        }

        public void setArtistList(List<ArtistList> artistList) {
            this.artistList = artistList;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getSongwriting() {
            return songwriting;
        }

        public void setSongwriting(String songwriting) {
            this.songwriting = songwriting;
        }

        public String getPicSinger() {
            return picSinger;
        }

        public void setPicSinger(String picSinger) {
            this.picSinger = picSinger;
        }

        public String getAlbum10001000() {
            return album10001000;
        }

        public void setAlbum10001000(String album10001000) {
            this.album10001000 = album10001000;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getAlbum500500() {
            return album500500;
        }

        public void setAlbum500500(String album500500) {
            this.album500500 = album500500;
        }

        public String getAliasname() {
            return aliasname;
        }

        public void setAliasname(String aliasname) {
            this.aliasname = aliasname;
        }

        public String getAlbumTitle() {
            return albumTitle;
        }

        public void setAlbumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
        }

        public String getKoreanBbSong() {
            return koreanBbSong;
        }

        public void setKoreanBbSong(String koreanBbSong) {
            this.koreanBbSong = koreanBbSong;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAllArtistId() {
            return allArtistId;
        }

        public void setAllArtistId(String allArtistId) {
            this.allArtistId = allArtistId;
        }

        public String getFileDuration() {
            return fileDuration;
        }

        public void setFileDuration(String fileDuration) {
            this.fileDuration = fileDuration;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public String getArtist10001000() {
            return artist10001000;
        }

        public void setArtist10001000(String artist10001000) {
            this.artist10001000 = artist10001000;
        }

        public String getResEncryptionFlag() {
            return resEncryptionFlag;
        }

        public void setResEncryptionFlag(String resEncryptionFlag) {
            this.resEncryptionFlag = resEncryptionFlag;
        }

        public String getAllRate() {
            return allRate;
        }

        public void setAllRate(String allRate) {
            this.allRate = allRate;
        }

        public String getArtistId() {
            return artistId;
        }

        public void setArtistId(String artistId) {
            this.artistId = artistId;
        }

        public String getDistribution() {
            return distribution;
        }

        public void setDistribution(String distribution) {
            this.distribution = distribution;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getPicSmall() {
            return picSmall;
        }

        public void setPicSmall(String picSmall) {
            this.picSmall = picSmall;
        }

        public String getOriginalRate() {
            return originalRate;
        }

        public void setOriginalRate(String originalRate) {
            this.originalRate = originalRate;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public String getPicPremium() {
            return picPremium;
        }

        public void setPicPremium(String picPremium) {
            this.picPremium = picPremium;
        }
    }

    public class SongUrl {

        @SerializedName("url")
        private List<Url> url;

        public List<Url> getUrl() {
            return url;
        }

        public void setUrl(List<Url> url) {
            this.url = url;
        }
    }

    public class Url {
        @SerializedName("show_link")
        private String showLink;

        @SerializedName("free")
        private int free;

        @SerializedName("replay_gain")
        private String replayGain;

        @SerializedName("hash")
        private String hash;

        @SerializedName("preload")
        private String preload;

        @SerializedName("can_load")
        private boolean canLoad;

        @SerializedName("file_format")
        private String fileFormat;

        @SerializedName("file_bitrate")
        private int fileBitrate;

        @SerializedName("file_link")
        private String fileLink;

        @SerializedName("down_type")
        private int downType;

        @SerializedName("song_file_id")
        private int songFileId;

        @SerializedName("file_size")
        private int fileSize;

        @SerializedName("can_see")
        private int canSee;

        @SerializedName("file_duration")
        private int fileDuration;

        @SerializedName("file_quality")
        private int fileQuality;

        @SerializedName("is_udition_url")
        private int isUditionUrl;

        @SerializedName("file_extension")
        private String fileExtension;

        @SerializedName("original")
        private int original;

        public String getShowLink() {
            return showLink;
        }

        public void setShowLink(String showLink) {
            this.showLink = showLink;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public String getReplayGain() {
            return replayGain;
        }

        public void setReplayGain(String replayGain) {
            this.replayGain = replayGain;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getPreload() {
            return preload;
        }

        public void setPreload(String preload) {
            this.preload = preload;
        }

        public boolean isCanLoad() {
            return canLoad;
        }

        public void setCanLoad(boolean canLoad) {
            this.canLoad = canLoad;
        }

        public String getFileFormat() {
            return fileFormat;
        }

        public void setFileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
        }

        public int getFileBitrate() {
            return fileBitrate;
        }

        public void setFileBitrate(int fileBitrate) {
            this.fileBitrate = fileBitrate;
        }

        public String getFileLink() {
            return fileLink;
        }

        public void setFileLink(String fileLink) {
            this.fileLink = fileLink;
        }

        public int getDownType() {
            return downType;
        }

        public void setDownType(int downType) {
            this.downType = downType;
        }

        public int getSongFileId() {
            return songFileId;
        }

        public void setSongFileId(int songFileId) {
            this.songFileId = songFileId;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public int getCanSee() {
            return canSee;
        }

        public void setCanSee(int canSee) {
            this.canSee = canSee;
        }

        public int getFileDuration() {
            return fileDuration;
        }

        public void setFileDuration(int fileDuration) {
            this.fileDuration = fileDuration;
        }

        public int getFileQuality() {
            return fileQuality;
        }

        public void setFileQuality(int fileQuality) {
            this.fileQuality = fileQuality;
        }

        public int getIsUditionUrl() {
            return isUditionUrl;
        }

        public void setIsUditionUrl(int isUditionUrl) {
            this.isUditionUrl = isUditionUrl;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public void setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
        }
    }

    public class ArtistList {

        @SerializedName("avatar_mini")
        private String avatarMini;
        @SerializedName("avatar_s300")
        private String avatarS300;
        @SerializedName("ting_uid")
        private String tingUid;
        @SerializedName("del_status")
        private String delStatus;
        @SerializedName("avatar_s500")
        private String avatarS500;
        @SerializedName("artist_name")
        private String artistName;
        @SerializedName("avatar_small")
        private String avatarSmall;
        @SerializedName("avatar_s180")
        private String avatarS180;
        @SerializedName("artist_id")
        private String artistId;

        public void setAvatarMini(String avatarMini) {
            this.avatarMini = avatarMini;
        }

        public String getAvatarMini() {
            return avatarMini;
        }

        public void setAvatarS300(String avatarS300) {
            this.avatarS300 = avatarS300;
        }

        public String getAvatarS300() {
            return avatarS300;
        }

        public void setTingUid(String tingUid) {
            this.tingUid = tingUid;
        }

        public String getTingUid() {
            return tingUid;
        }

        public void setDelStatus(String delStatus) {
            this.delStatus = delStatus;
        }

        public String getDelStatus() {
            return delStatus;
        }

        public void setAvatarS500(String avatarS500) {
            this.avatarS500 = avatarS500;
        }

        public String getAvatarS500() {
            return avatarS500;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public String getArtistName() {
            return artistName;
        }

        public void setAvatarSmall(String avatarSmall) {
            this.avatarSmall = avatarSmall;
        }

        public String getAvatarSmall() {
            return avatarSmall;
        }

        public void setAvatarS180(String avatarS180) {
            this.avatarS180 = avatarS180;
        }

        public String getAvatarS180() {
            return avatarS180;
        }

        public void setArtistId(String artistId) {
            this.artistId = artistId;
        }

        public String getArtistId() {
            return artistId;
        }

    }
}
