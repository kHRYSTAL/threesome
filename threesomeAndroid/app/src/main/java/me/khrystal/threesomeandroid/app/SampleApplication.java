package me.khrystal.threesomeandroid.app;

import android.app.Application;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/21
 * update time:
 * email: 723526676@qq.com
 */

public class SampleApplication extends Application {

    public static SampleApplication CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
    }
}
