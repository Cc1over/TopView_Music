package com.hebaiyi.www.topviewmusic.music.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hebaiyi.www.topviewmusic.base.activity.PresenterActivity;
import com.hebaiyi.www.topviewmusic.bean.BottomMusic;
import com.hebaiyi.www.topviewmusic.bean.LocalMusic;
import com.hebaiyi.www.topviewmusic.music.contract.MusicContract;
import com.hebaiyi.www.topviewmusic.music.presenter.MusicPresenterImp;

public class MusicActivity
        extends PresenterActivity<MusicContract.MusicView, MusicPresenterImp>
        implements MusicContract.MusicView {

    private LocalMusic mLocalMusic;

    public static void actionStart(Context context,LocalMusic lm){
        Intent i = new Intent(context,MusicActivity.class);
        i.putExtra("local_music",lm);
        context.startActivity(i);
    }

    @Override
    protected MusicPresenterImp createPresenter() {
        return new MusicPresenterImp(this);
    }



    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void getBottomState(BottomMusic music) {

    }

    @Override
    protected BottomMusic setBottomState() {
        return null;
    }

}
