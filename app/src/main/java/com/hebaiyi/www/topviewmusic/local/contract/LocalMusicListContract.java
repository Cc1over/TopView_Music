package com.hebaiyi.www.topviewmusic.local.contract;

import android.content.Context;

import com.hebaiyi.www.topviewmusic.bean.Music;

import java.util.List;

public class LocalMusicListContract {

    public interface LocalMusicListView{
        void showLocalMusicList(List<Music> musics);
    }

    public interface LocalMusicListPresenter{
       void obtainLocalMusicList(Context context);
    }

}
