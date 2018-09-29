package com.hebaiyi.www.topviewmusic.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hebaiyi.www.topviewmusic.R;

public class SidebarItemView extends LinearLayout {

    private LinearLayout mLayout;
    private TextView mTvTitle;
    private int screenWidth;

    public SidebarItemView(Context context) {
        super(context);
        init();
    }

    public SidebarItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SidebarItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_sidebar_item, this);
        mLayout = findViewById(R.id.sidebar_item_root);
        mTvTitle = findViewById(R.id.sidebar_item_title);
        screenWidth = obtainScreenWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / 4,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayout.setLayoutParams(lp);


    }

    private int obtainScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public void setText(String text) {
        mTvTitle.setText(text);
    }

    public void setItemAnimation() {
        setAnimation(-(screenWidth / 20), -(screenWidth / 20));
    }

    public void setItemAnimation2() {
        setAnimation(-(screenWidth / 30), -(screenWidth / 30));
    }

    private void setAnimation(float fromXDelta, float toXDelta) {
        AnimationSet set = new AnimationSet(true);
        final TranslateAnimation animation =
                new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
        animation.setDuration(50);
        animation.setFillAfter(false);
        set.addAnimation(animation);
        mTvTitle.startAnimation(animation);
        animation.startNow();
    }

}




