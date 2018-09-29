package com.hebaiyi.www.topviewmusic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hebaiyi.www.topviewmusic.R;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends RelativeLayout {

    // 水平间距
    private int horizontalSpacing;
    // 竖直间距
    private int verticalSpacing;
    // 行的集合
    private List<Line> lines = new ArrayList<>();
    // 当前行
    private Line line;
    // 当前行使用的空间
    private int lineSize = 0;
    // 关键字字体大小
    private int textSize;
    // 关键字颜色
    private int textColor;
    // 关键字背景
    private int textBackgroundResource;
    // 关键字水平padding
    private int textPaddingH;
    // 关键字竖直padding
    private int textPaddingV;


    public FlowLayout(Context context) {
        super(context);
        init(null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setFlowLayout(List<String> list, final OnItemClickListener listener){
        for (int i = 0; i < list.size(); i++) {
            final TextView tv = new TextView(getContext());
            // 设置TextView属性
            tv.setText(list.get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            tv.setTextColor(textColor);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
            tv.setClickable(true);
            tv.setBackgroundResource(textBackgroundResource);
            this.addView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(tv.getText().toString());
                }
            });
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        horizontalSpacing = ta.getDimensionPixelSize(
                R.styleable.FlowLayout_horizontalSpacing, dip2px(10));
        verticalSpacing = ta.getDimensionPixelSize(
                R.styleable.FlowLayout_verticalSpacing, dip2px(10));
        textSize = ta.getDimensionPixelSize(
                R.styleable.FlowLayout_textSize, sp2px(15));
        textColor = ta.getColor(
                R.styleable.FlowLayout_textColor, Color.BLACK);
        textBackgroundResource = ta.getResourceId(
                R.styleable.FlowLayout_textBackground,R.drawable.flow_layout_bg);
        textPaddingH = ta.getDimensionPixelSize(
                R.styleable.FlowLayout_textPaddingH, dip2px(7));
        textPaddingV = ta.getDimensionPixelSize(
                R.styleable.FlowLayout_textPaddingV, dip2px(4));
        ta.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.layout(left, top);
            // 计算下一行的起点y轴坐标
            top = top + line.getHeight() + verticalSpacing;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 实际可以用的宽和高
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingStart() - getPaddingEnd();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // Line初始化
        restoreLine();
        for (int i = 0;i<getChildCount();i++) {
            View child = getChildAt(i);
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                    widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            if (line == null) {
                // 创建新一行
                line = new Line();
            }
            // 计算当前行已使用的宽度
            int measuredWidth = child.getMeasuredWidth();
            lineSize += measuredWidth;
            // 如果使用的宽度小于可用的宽度，这时候childView能够添加到当前的行上
            if (lineSize <= width) {
                line.addChild(child);
                lineSize += horizontalSpacing;
            } else {
                // 换行
                newLine();
                line.addChild(child);
                lineSize += child.getMeasuredWidth();
                lineSize += horizontalSpacing;
            }
        }
        if(line!=null&&!lines.contains(line)){
            lines.add(line);
        }
        int totalHeight = 0;
        // 把所有行的高度加上
        for (int i = 0; i < lines.size(); i++) {
            totalHeight += lines.get(i).getHeight();
        }
        // 加上行的竖直间距
        totalHeight += verticalSpacing * (lines.size() - 1);
        // 加上上下padding
        totalHeight += getPaddingBottom();
        totalHeight += getPaddingTop();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                resolveSize(totalHeight, heightMeasureSpec));
    }

    /**
     * Line初始化
     */
    private void restoreLine() {
        lines.clear();
        line = new Line();
        lineSize = 0;
    }

    /**
     *  换行
     */
    private void newLine(){
       // 把之前的行记录下来
        if (line != null) {
            lines.add(line);
        }
        // 创建新的一行
        line = new Line();
        lineSize = 0;
    }

    /**
     * 管理每行上的view对象
     */
    private class Line {
        // 子控件集合
        private List<View> children = new ArrayList<>();
        // 行高
        private int height;

        /**
         * 添加childView
         *
         * @param childView 子控件
         */
        private void addChild(View childView) {
            children.add(childView);
            // 让当前的行高是最高的一个childView的高度
            if (height < childView.getMeasuredHeight()) {
                height = childView.getMeasuredHeight();
            }
        }


        /**
         * 设置childView的绘制区域
         *
         * @param left 左上角x坐标
         * @param top  左上角y坐标
         */
        private void layout(int left, int top) {
            int totalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            // 当前childView的左上角x轴坐标
            int currentLeft = left;
            for (int i = 0; i < children.size(); i++) {
                View view = children.get(i);
                // 设置childView的绘制区域
                view.layout(currentLeft, top, currentLeft + view.getMeasuredWidth(),
                        top + view.getMeasuredHeight());
                // 计算下一个childView的位置
                currentLeft = currentLeft + view.getMeasuredWidth() + horizontalSpacing;
            }
        }

        private int getHeight() {
            return height;
        }

        private int getChildCount() {
            return children.size();
        }

    }

    public interface OnItemClickListener{
        void onItemClick(String content);
    }

    public void setTextSize(int size){
        textSize = size;
    }

    public void setTextBackground(int rs){
        textBackgroundResource = rs;
    }

    public void setTextColor(int color){
        textColor = color;
    }

    public void setTextPaddingH(int ph){
        textPaddingH = ph;
    }

    public void setTextPaddingV(int pv){
        textPaddingV = pv;
    }

    public void setHorizontalSpacing(int hs){
        horizontalSpacing = hs;
    }

    public void setVerticalSpacing(int vs){
        verticalSpacing = vs;
    }


    //----------------------------------工具方法----------------------------------------------//

    /**
     * 把dp转化为px
     *
     * @param dpValue dp数据
     * @return 对应的px数据
     */
    private int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 把sp转化成px
     *
     * @param spValue sp数据
     * @return 对应的px数据
     */
    private int sp2px(float spValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }


    /**
     * 把px转化成sp
     *
     * @param pxValue px数据
     * @return 对应的px数据
     */
    private int px2sp(float pxValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 把px转化为dp
     *
     * @param pxValue px数据
     * @return 对应的dp数据
     */
    private int px2dip(float pxValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
