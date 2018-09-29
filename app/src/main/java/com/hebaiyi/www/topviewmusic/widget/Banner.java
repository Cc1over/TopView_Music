package com.hebaiyi.www.topviewmusic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hebaiyi.www.topviewmusic.R;

import java.util.ArrayList;
import java.util.List;

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final int DELAY_TIME = 3000;
    private boolean isAuto = true;
    private List<String> mUrls;
    private List<ImageView> mViewpagerViews;
    private List<ImageView> mDotImageViews;
    private Context context;
    // 原点大小
    private int size;
    // 原点的间距
    private int spacing;
    // viewpager中view的数量
    private int count;
    // 圆点布局
    private LinearLayout mDotLayout;
    private ViewPager mViewPager;
    private BannerAdapter mAdapter;
    private AutoTask mTask;
    private OnBannerClickListener mListener;
    // 当前viewpager的索引
    private int currentItem;
    private Handler mHandler = new Handler();

    public Banner(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        // 初始化变量
        mUrls = new ArrayList<>();
        mViewpagerViews = new ArrayList<>();
        mDotImageViews = new ArrayList<>();
        // 获取属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        size = ta.getDimensionPixelSize(R.styleable.Banner_size, 10);
        spacing = ta.getDimensionPixelSize(R.styleable.Banner_spacing, 10);
        count = ta.getDimensionPixelSize(R.styleable.Banner_count, 6);
        ta.recycle();
        // 初始化viewPager
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_banner, this, true);
        mViewPager = view.findViewById(R.id.banner_vp);
        mViewPager.addOnPageChangeListener(this);
        // 初始化圆点布局
        mDotLayout = view.findViewById(R.id.banner_ll_dots);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < count; i++) {
            if (i == position) {
                mDotImageViews.get(i).setImageResource(R.drawable.banner_dot_red);
            } else {
                mDotImageViews.get(i).setImageResource(R.drawable.banner_dot_white);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     *  外界设置是否需要自动变化，默认值为true
     * @param isAuto 是否需要自动变化
     */
    public void isAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

    /**
     * 设置自动滚动
     */
    public void setAuto() {
        mTask = new AutoTask();
        mHandler.postDelayed(mTask,DELAY_TIME);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void display() {
        //绘制viewpager
        drawViewpager();
        //绘制圆点
        drawDots();
        //设置自动滚动
        setAuto();
    }

    private void drawViewpager() {
        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mViewpagerViews.add(iv);
        }
        if (mViewpagerViews != null) {
            mAdapter = new BannerAdapter();
            mViewPager.setAdapter(mAdapter);
        }
    }

    private void drawDots() {
        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.leftMargin = spacing;
            params.rightMargin = spacing;
            iv.setLayoutParams(params);
            mDotImageViews.add(iv);
            if (i == 0) {
                iv.setImageResource(R.drawable.banner_dot_red);
            } else {
                iv.setImageResource(R.drawable.banner_dot_white);
            }
            mDotLayout.addView(iv);
        }
    }

    private class AutoTask implements Runnable {

        @Override
        public void run() {
            currentItem++;
            if (currentItem >= count) {
                currentItem = 0;
            }
            mViewPager.setCurrentItem(currentItem);
            mHandler.postDelayed(this, DELAY_TIME);
        }
    }

    /**
     * 处理自动轮播和手动滑动的冲突
     *
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            stopAuto();
        } else if (action == MotionEvent.ACTION_UP) {
            setAuto();
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 供外部调用者初始化接口对象
     */
    public void setOnBannerClickListener(OnBannerClickListener bannerClickListener) {
        mListener = bannerClickListener;
    }

    //取消轮播任务
    public void cancel(){
        mHandler.removeCallbacks(mTask);
    }

    /**
     * 取消自动轮播任务
     */
    private void stopAuto() {
        mHandler.removeCallbacks(mTask);
    }


    public Banner loadData(List<String> urls) {
        this.mUrls = urls;
        this.count = urls.size();
        return this;
    }


    private class BannerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView iv = mViewpagerViews.get(position);
            container.addView(iv);
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null) {
                        mListener.onClick(position);
                    }
                }
            });
            Glide.with(context)
                    .load(mUrls.get(position))
                    .into(iv);
            return iv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mViewpagerViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

    }

    public interface OnBannerClickListener {
        void onClick(int position);
    }

}
