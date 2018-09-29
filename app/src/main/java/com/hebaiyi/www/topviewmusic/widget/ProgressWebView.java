package com.hebaiyi.www.topviewmusic.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ProgressWebView extends WebView {

    private ProgressBar mProgressBar;


    public ProgressWebView(Context context) {
        super(context);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mProgressBar = new ProgressBar(context,null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,8);
        mProgressBar.setLayoutParams(lp);
        addView(mProgressBar);
        setWebChromeClient(new WebChromeClient());
    }

    private class WebChromeClient extends android.webkit.WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE) {
                    mProgressBar.setVisibility(VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

}
