package com.hebaiyi.www.topviewmusic.search.contract;

import android.os.Parcel;
import android.os.Parcelable;

import com.hebaiyi.www.topviewmusic.bean.HotWord;
import com.hebaiyi.www.topviewmusic.bean.SearchMerge;

import java.util.List;

public class SearchContract {

    public interface SearchView {
        void showHotWords(List<HotWord> hotWords);

        void showSearchMerge(MergeSet ms);
    }

    public interface SearchPresenter {
        void obtainHotWords();

        void obtainSearchMerge(String query, int pageNo, int pageSize);
    }


    public static class MergeSet implements Parcelable {
        private List<SearchMerge.AlbumInfo> ais;
        private List<SearchMerge.ArtistInfo> ars;
        private List<SearchMerge.SongInfo> sis;

        public MergeSet(List<SearchMerge.AlbumInfo> ais,
                        List<SearchMerge.ArtistInfo> ars,
                        List<SearchMerge.SongInfo> sis) {
            this.ais = ais;
            this.ars = ars;
            this.sis = sis;
        }

        protected MergeSet(Parcel in) {
        }

        public static final Creator<MergeSet> CREATOR = new Creator<MergeSet>() {
            @Override
            public MergeSet createFromParcel(Parcel in) {
                return new MergeSet(in);
            }

            @Override
            public MergeSet[] newArray(int size) {
                return new MergeSet[size];
            }
        };

        public List<SearchMerge.AlbumInfo> getAlbumInfos() {
            return ais;
        }

        public List<SearchMerge.ArtistInfo> getArtistInfos() {
            return ars;
        }

        public List<SearchMerge.SongInfo> getSongInfos() {
            return sis;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

}
