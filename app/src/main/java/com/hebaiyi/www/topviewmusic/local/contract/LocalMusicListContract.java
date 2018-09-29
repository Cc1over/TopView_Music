package com.hebaiyi.www.topviewmusic.local.contract;

import android.content.Context;

import com.hebaiyi.www.topviewmusic.bean.LocalMusic;

import java.util.List;

public class LocalMusicListContract {

    public interface LocalMusicListView{
        void showLocalMusicList(List<LocalMusic> musics);
    }

    public interface LocalMusicListPresenter{
       void obtainLocalMusicList(Context context);
    }

}
