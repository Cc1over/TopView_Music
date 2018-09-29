package com.hebaiyi.www.topviewmusic.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化变量
        initVariables();
        // 初始化布局
        initView(savedInstanceState);
        // 初始化点击事件
        initEvents();
        // 调用API数据
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initVariables();

    protected abstract void loadData();

    protected abstract void initEvents();


    /**
     * 初始化toolbar
     */
    protected void initToolbar(Toolbar tb, int resId) {
        tb.setTitle("");
        setSupportActionBar(tb);
        if (getSupportActionBar() == null) {
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(resId);
    }


}
