package com.hebaiyi.www.topviewmusic.recommend.contract;

import com.hebaiyi.www.topviewmusic.bean.FoucsPic;
import com.hebaiyi.www.topviewmusic.bean.GeDan;
import com.hebaiyi.www.topviewmusic.bean.RecommendRadio;
import com.hebaiyi.www.topviewmusic.bean.Song;

import java.util.List;

public class RecommendContract {

    public interface RecommendView {
        void showFoucsPic(List<FoucsPic> pics);
        void showRecommendSongList(List<Song> songs);
        void showGeDan(List<GeDan> geDans);
        void showRadio(List<RecommendRadio> radios);
    }

    public interface RecommendPresenter {
        void obtainFoucsPic(int num);
        void obtainRecommendSongList(int num);
        void obtainGeDan(int num, int pageNo);
        void obtainRecommendRadio(int num);
    }


}
