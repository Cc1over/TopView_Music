package com.hebaiyi.www.topviewmusic.util;

import android.content.Context;

public class ViewUtil {
    /**
     * 根据手机分辨率从dp的单位转换成px
     *
     * @param context 对应的上下文
     * @param dpValue dp值
     * @return 相应的px值
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px的单位转化为dp
     *
     * @param context 对应的上下文
     * @param pxValue px值
     * @return 相应的dp值
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 把sp转化成px
     *
     * @param context 上下文
     * @param spValue sp数据
     * @return 对应的px数据
     */
    private static int sp2px(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }


    /**
     * 把px转化成sp
     *
     * @param context 上下文
     * @param pxValue px数据
     * @return 对应的px数据
     */
    private static int px2sp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }


}
