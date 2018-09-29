package com.hebaiyi.www.topviewmusic.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hebaiyi.www.topviewmusic.R;

import java.util.ArrayList;
import java.util.List;

public class SidebarView extends LinearLayout implements View.OnTouchListener {

    private LinearLayout mLayout;
    private TextView mTitle;
    private boolean isDown = false;
    private OnSidebarViewListener listener;

    public SidebarView(Context context) {
        super(context);
        init();
    }

    public SidebarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SidebarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_sidebar, this);
        mLayout = findViewById(R.id.sidebar_layout);
        mTitle = findViewById(R.id.sidebar_title);
        mLayout.setOnTouchListener(this);
        List<String> listData = new ArrayList<>();
        listData.add("A");
        listData.add("B");
        listData.add("C");
        listData.add("D");
        listData.add("E");
        listData.add("F");
        listData.add("G");
        listData.add("H");
        listData.add("I");
        listData.add("J");
        listData.add("K");
        listData.add("L");
        listData.add("M");
        listData.add("N");
        listData.add("O");
        listData.add("P");
        listData.add("Q");
        listData.add("R");
        listData.add("S");
        listData.add("T");
        listData.add("U");
        listData.add("V");
        listData.add("W");
        listData.add("X");
        listData.add("Y");
        listData.add("Z");
        setSideDae(listData);
    }

    private void setSideDae(List<String> listData) {
        mLayout.removeAllViews();
        for (String s : listData) {
            SidebarItemView sv = new SidebarItemView(getContext());
            sv.setTag(s);
            sv.setText(s);
            mLayout.addView(sv);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isDown = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP ||
                event.getAction() == MotionEvent.ACTION_CANCEL) {
            mTitle.setVisibility(View.GONE);
            isDown = false;
        }
        transformValue((LinearLayout) v, event);
        return true;
    }

    private void transformValue(LinearLayout layout, MotionEvent motionEvent) {
        SidebarItemView textView = (SidebarItemView) layout.getChildAt(0);
        float fistViewTop = textView.getTop();
        float itemHeight = textView.getMeasuredHeight();
        float currentY = motionEvent.getY();
        //不超过顶部和顶部
        if (motionEvent.getY() > 0 && motionEvent.getY() < layout.getMeasuredHeight()) {            //不超过子控件的范围值            if (currentY > fistViewTop && currentY < (fistViewTop + itemHeight * layout.getChildCount())) {                float rang = currentY - fistViewTop;                int index = (int) (rang / itemHeight);                Log.e("kawa", "rang:" + rang + "_index:" + index + "_itemHeight:" + itemHeight);                vTitle.setText((String) (layout.getChildAt(index).getTag()));                if (listener!=null){                    listener.onSelectItem(index,(String) (layout.getChildAt(index).getTag()));                }                if (isDown) {                    vTitle.setVisibility(View.VISIBLE);                    setAnimation(index, layout);                }            }        }
            if (currentY > fistViewTop && currentY <
                    (fistViewTop + itemHeight * layout.getChildCount())) {
                float rang = currentY - fistViewTop;
                int index = (int) (rang / itemHeight);
                mTitle.setText((String) (layout.getChildAt(index).getTag()));
                if (listener != null) {
                    listener.onSelectItem(index, (String) (layout.getChildAt(index).getTag()));
                }
                if (isDown) {
                    mTitle.setVisibility(View.VISIBLE);
                    setAnimation(index, layout);
                }
            }
        }
    }

    private void setAnimation(int index, LinearLayout layout) {
        if (index > 1 && index < layout.getChildCount() - 2) {
            SidebarItemView tv1 = (SidebarItemView) layout.getChildAt(index - 1);
            SidebarItemView tv2 = (SidebarItemView) layout.getChildAt(index);
            SidebarItemView tv3 = (SidebarItemView) layout.getChildAt(index + 1);
            tv1.setItemAnimation2();
            tv2.setItemAnimation();
            tv3.setItemAnimation2();
        } else {
            if (index == 0) {
                SidebarItemView tv1 = (SidebarItemView) layout.getChildAt(index);
                SidebarItemView tv2 = (SidebarItemView) layout.getChildAt(index + 1);
                tv1.setItemAnimation();
                tv2.setItemAnimation2();
            } else {
                SidebarItemView tv1 = (SidebarItemView) layout.getChildAt(index);
                SidebarItemView tv2 = (SidebarItemView) layout.getChildAt(index - 1);
                tv1.setItemAnimation();
                tv2.setItemAnimation2();
            }
        }
    }

    public void setOnSidebarViewListener(OnSidebarViewListener l) {
        listener = l;
    }

    public interface OnSidebarViewListener {
        void onSelectItem(int index, String value);
    }


}