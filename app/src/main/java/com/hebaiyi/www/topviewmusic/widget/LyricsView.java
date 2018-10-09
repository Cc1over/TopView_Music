package com.hebaiyi.www.topviewmusic.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.hebaiyi.www.topviewmusic.R;
import com.hebaiyi.www.topviewmusic.bean.Lyrics;
import com.hebaiyi.www.topviewmusic.music.view.LyricsFragment;

import java.util.List;


/**
 * 自定义LrcView,可以同步显示歌词，拖动歌词，缩放歌词
 */
public class LyricsView extends View {

    public final static int DISPLAY_MODE_NORMAL = 0;
    public final static int DISPLAY_MODE_SEEK = 1;
    public final static int DISPLAY_MODE_SCALE = 2;
    private int mDisplayMode = DISPLAY_MODE_NORMAL;
    private List<Lyrics> mLrcRows;
    private int mMinSeekFiredOffset = 10;
    private int mHighlightRow = 0;
    private int mHignlightRowColor = getResources().getColor(R.color.red);
    private int mNormalRowColor = getResources().getColor(R.color.white);
    private int mSeekLineColor;
    private int mSeekLineTextColor;
    private int mSeekLineTextSize;
    private int mMinSeekLineTextSize;
    private int mMaxSeekLineTextSize;
    private int mLrcFontSize;
    private int mMinLrcFontSize;
    private int mMaxLrcFontSize;
    private int mPaddingY;
    private int mSeekLinePaddingX;
    private ILrcViewListener mLrcViewListener;
    private String mLoadingLrcTip = "暂无歌词";
    private Paint mPaint;
    private Fragment mFragment;
    private Scroller mScroller;

    public LyricsView(Context context) {
        super(context);
        init();
    }

    public LyricsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LyricsView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    private void init() {
        TypedArray ta = getContext().obtainStyledAttributes(R.styleable.LyricsView);
        mMinSeekFiredOffset = ta.getDimensionPixelOffset(
                R.styleable.LyricsView_min_seek_fired_offset, dip2px(15));
        mHignlightRowColor = ta.getColor(
                R.styleable.LyricsView_high_line_row_color, getResources().getColor(R.color.red));
        mNormalRowColor = ta.getColor(
                R.styleable.LyricsView_normal_color, getResources().getColor(R.color.white));
        mSeekLineColor = ta.getColor(
                R.styleable.LyricsView_seek_line_color, getResources().getColor(R.color.red));
        mSeekLineTextColor = ta.getColor(
                R.styleable.LyricsView_seek_line_text_color, getResources().getColor(R.color.red));
        mSeekLineTextSize = ta.getDimensionPixelSize(
                R.styleable.LyricsView_seek_line_text_size, dip2px(18));
        mMinSeekLineTextSize = ta.getDimensionPixelSize(
                R.styleable.LyricsView_min_seek_line_text_size, dip2px(18));
        mMaxSeekLineTextSize = ta.getDimensionPixelSize(
                R.styleable.LyricsView_max_seeK_line_text_size, dip2px(22));
        mLrcFontSize = ta.getDimensionPixelSize(
                R.styleable.LyricsView_lyrics_text_size, dip2px(18));
        mMinLrcFontSize = ta.getDimensionPixelSize(
                R.styleable.LyricsView_min_lyrics_text_size, dip2px(18));
        mMaxLrcFontSize = ta.getDimensionPixelSize(
                R.styleable.LyricsView_max_lyrics_text_size, dip2px(22));
        mPaddingY = ta.getDimensionPixelOffset(
                R.styleable.LyricsView_padding_Y, dip2px(20));
        mSeekLinePaddingX = ta.getDimensionPixelOffset(
                R.styleable.LyricsView_seek_line_padding_X, dip2px(0));
        ta.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mLrcFontSize);
        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void setListener(ILrcViewListener l) {
        mLrcViewListener = l;
    }

    public void setLoadingTipText(String text) {
        mLoadingLrcTip = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int width = getWidth();
        if (mLrcRows == null || mLrcRows.size() == 0) {
            if (mLoadingLrcTip != null) {
                mPaint.setColor(mHignlightRowColor);
                mPaint.setTextSize(mLrcFontSize);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mLoadingLrcTip, width / 2, height / 2 - mLrcFontSize, mPaint);
            }
            return;
        }
        int rowY = 0;
        final int rowX = width / 2;
        int rowNum = 0;
        String highlightText = mLrcRows.get(mHighlightRow).getContent();
        int highlightRowY = height / 2 - mLrcFontSize;
        mPaint.setColor(mHignlightRowColor);
        mPaint.setTextSize(mLrcFontSize);
        mPaint.setTextAlign(Align.CENTER);
        if (!(highlightText == null || "".equals(highlightText))) {
            canvas.drawText(highlightText, rowX, highlightRowY, mPaint);
        }
        if (mDisplayMode == DISPLAY_MODE_SEEK) {
            mPaint.setColor(mSeekLineColor);
            canvas.drawLine(mSeekLinePaddingX, highlightRowY,
                    width - mSeekLinePaddingX, highlightRowY, mPaint);
            mPaint.setColor(mSeekLineTextColor);
            mPaint.setTextSize(mSeekLineTextSize);
            mPaint.setTextAlign(Align.LEFT);
            if (mLrcRows.get(mHighlightRow).getStrTime() != null) {
                canvas.drawText(mLrcRows.get(mHighlightRow).getStrTime(), 0, highlightRowY, mPaint);
            }
        }
        mPaint.setColor(mNormalRowColor);
        mPaint.setTextSize(mLrcFontSize);
        mPaint.setTextAlign(Align.CENTER);
        rowNum = mHighlightRow - 1;
        rowY = highlightRowY - mPaddingY - mLrcFontSize;
        while (rowY > -mLrcFontSize && rowNum >= 0) {
            String text = mLrcRows.get(rowNum).getContent();
            if (text != null && !"".equals(text)) {
                canvas.drawText(text, rowX, rowY, mPaint);
            }
            rowY -= (mPaddingY + mLrcFontSize);
            rowNum--;
        }
        rowNum = mHighlightRow + 1;
        rowY = highlightRowY + mPaddingY + mLrcFontSize;
        while (rowY < height && rowNum < mLrcRows.size()) {
            String text = mLrcRows.get(rowNum).getContent();
            if (!(text == null || "".equals(text))) {
                canvas.drawText(text, rowX, rowY, mPaint);
            }
            rowY += (mPaddingY + mLrcFontSize);
            rowNum++;
        }

    }

    public void seekLrc(int position, boolean cb) {
        if (mLrcRows == null || position < 0 || position > mLrcRows.size()) {
            return;
        }
        Lyrics lrcRow = mLrcRows.get(position);
        mHighlightRow = position;
        invalidate();
        if (mLrcViewListener != null && cb) {
            mLrcViewListener.onLrcSeeked(position, lrcRow);
        }
    }

    public void attach(Fragment fragment) {
        mFragment = fragment;
    }

    private float mLastMotionY;
    private PointF mPointerOneLastMotion = new PointF();
    private PointF mPointerTwoLastMotion = new PointF();
    private float mNowMotionY;
    private boolean mIsFirstMove = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLrcRows == null || mLrcRows.size() == 0) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = event.getY();
                mIsFirstMove = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    doScale(event);
                    return true;
                }
                if (mDisplayMode == DISPLAY_MODE_SCALE) {
                    return true;
                }
                doSeek(event);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mNowMotionY = event.getY();
                if (Math.abs(mNowMotionY - mLastMotionY) < 1) {
                    ((LyricsFragment) mFragment).replace();
                }
                if (mDisplayMode == DISPLAY_MODE_SEEK) {
                    seekLrc(mHighlightRow, true);
                }
                mDisplayMode = DISPLAY_MODE_NORMAL;
                invalidate();
                break;
        }
        return true;
    }

    private void doScale(MotionEvent event) {
        if (mDisplayMode == DISPLAY_MODE_SEEK) {
            mDisplayMode = DISPLAY_MODE_SCALE;
            return;
        }
        if (mIsFirstMove) {
            mDisplayMode = DISPLAY_MODE_SCALE;
            invalidate();
            mIsFirstMove = false;
            setTwoPointerLocation(event);
        }
        int scaleSize = getScale(event);
        if (scaleSize != 0) {
            setNewFontSize(scaleSize);
            invalidate();
        }
        setTwoPointerLocation(event);
    }

    private void doSeek(MotionEvent event) {
        float y = event.getY();
        float offsetY = y - mLastMotionY;
        if (Math.abs(offsetY) < mMinSeekFiredOffset) {
            return;
        }
        mDisplayMode = DISPLAY_MODE_SEEK;
        int rowOffset = Math.abs((int) offsetY / mLrcFontSize);
        if (offsetY < 0) {
            mHighlightRow += rowOffset;
        } else if (offsetY > 0) {
            mHighlightRow -= rowOffset;
        }
        mHighlightRow = Math.max(0, mHighlightRow);
        mHighlightRow = Math.min(mHighlightRow, mLrcRows.size() - 1);
        if (rowOffset > 0) {
            mLastMotionY = y;
            invalidate();
        }
    }

    private void setTwoPointerLocation(MotionEvent event) {
        mPointerOneLastMotion.x = event.getX(0);
        mPointerOneLastMotion.y = event.getY(0);
        mPointerTwoLastMotion.x = event.getX(1);
        mPointerTwoLastMotion.y = event.getY(1);
    }

    private void setNewFontSize(int scaleSize) {
        mLrcFontSize += scaleSize;
        mLrcFontSize = Math.max(mLrcFontSize, mMinLrcFontSize);
        mLrcFontSize = Math.min(mLrcFontSize, mMaxLrcFontSize);
        mSeekLineTextSize += scaleSize;
        mSeekLineTextSize = Math.max(mSeekLineTextSize, mMinSeekLineTextSize);
        mSeekLineTextSize = Math.min(mSeekLineTextSize, mMaxSeekLineTextSize);
    }

    private int getScale(MotionEvent event) {
        float x0 = event.getX(0);
        float y0 = event.getY(0);
        float x1 = event.getX(1);
        float y1 = event.getY(1);
        float maxOffset = 0;
        boolean zoomin = false;
        float oldXOffset = Math.abs(mPointerOneLastMotion.x - mPointerTwoLastMotion.x);
        float newXoffset = Math.abs(x1 - x0);
        float oldYOffset = Math.abs(mPointerOneLastMotion.y - mPointerTwoLastMotion.y);
        float newYoffset = Math.abs(y1 - y0);
        maxOffset = Math.max(Math.abs(newXoffset - oldXOffset), Math.abs(newYoffset - oldYOffset));
        if (maxOffset == Math.abs(newXoffset - oldXOffset)) {
            zoomin = newXoffset > oldXOffset;
        } else {
            zoomin = newYoffset > oldYOffset;
        }
        if (zoomin) {
            return (int) (maxOffset / 10);
        } else {
            return -(int) (maxOffset / 10);
        }
    }

    public void setLyrics(List<Lyrics> lrcRows) {
        mLrcRows = lrcRows;
        invalidate();
    }

    public void seekLrcToTime(long time) {
        if (mLrcRows == null || mLrcRows.size() == 0) {
            return;
        }
        if (mDisplayMode != DISPLAY_MODE_NORMAL) {
            return;
        }
        for (int i = 0; i < mLrcRows.size(); i++) {
            Lyrics current = mLrcRows.get(i);
            Lyrics next = i + 1 == mLrcRows.size() ? null : mLrcRows.get(i + 1);
            if ((time >= current.getTime() && next != null && time < next.getTime())
                    || (time > current.getTime() && next == null)) {
                seekLrc(i, false);
                return;
            }
        }
    }

    public interface ILrcViewListener {
        void onLrcSeeked(int position, Lyrics lrcRow);
    }

    private int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
