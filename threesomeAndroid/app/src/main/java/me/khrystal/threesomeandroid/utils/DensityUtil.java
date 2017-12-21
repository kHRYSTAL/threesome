package me.khrystal.threesomeandroid.utils;

import android.content.Context;

import me.khrystal.threesomeandroid.app.SampleApplication;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/21
 * update time:
 * email: 723526676@qq.com
 */

public class DensityUtil {

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(float dipValue) {
        float scale = SampleApplication.CONTEXT.getResources()
                .getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        float scale = SampleApplication.CONTEXT.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
