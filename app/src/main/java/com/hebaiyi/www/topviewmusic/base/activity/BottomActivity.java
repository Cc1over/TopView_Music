package com.hebaiyi.www.topviewmusic.base.activity;

import android.os.Bundle;

import com.hebaiyi.www.topviewmusic.bean.Music;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public abstract class BottomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        if (setBottomState() != null) {
            EventBus.getDefault().postSticky(setBottomState());
        }
        super.onPause();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MainThread)
    public void onEvent(Music music) {
        getBottomState(music);
    }

    protected abstract void getBottomState(Music music);

    protected abstract Music setBottomState();


}
