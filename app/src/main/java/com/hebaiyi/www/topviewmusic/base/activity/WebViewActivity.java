package com.hebaiyi.www.topviewmusic.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.hebaiyi.www.topviewmusic.R;

public class WebViewActivity extends BaseActivity {

    private WebView mWvContent;
    private Toolbar mTbTitle;
    private String url;
    private String title;

    public static void actionStart(Context context, String url, String title) {
        Intent i = new Intent(context, WebViewActivity.class);
        i.putExtra("url", url);
        i.putExtra("title", title);
        context.startActivity(i);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        mTbTitle = findViewById(R.id.wvAty_tb_title);
        mWvContent = findViewById(R.id.wvAty_wv_content);
        initToolbar(mTbTitle, R.drawable.back_icon);
    }

    @Override
    protected void initVariables() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void loadData() {
         mTbTitle.setTitle(title);
         mWvContent.getSettings().setJavaScriptEnabled(true);
         mWvContent.setWebViewClient(new WebViewClient());
         mWvContent.loadUrl(url);
    }

    @Override
    protected void initEvents() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
