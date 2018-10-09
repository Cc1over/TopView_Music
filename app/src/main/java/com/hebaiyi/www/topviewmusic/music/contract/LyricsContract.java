package com.hebaiyi.www.topviewmusic.music.contract;

import com.hebaiyi.www.topviewmusic.bean.Lyrics;

import java.util.List;

public class LyricsContract {

    public interface LyricsView {
        void showLyrics(List<Lyrics> lyricses);
    }

    public interface LyricsPresenter {
        void obtainLyrics(String path);
    }

}
