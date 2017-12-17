package me.khrystal.threesomeandroid;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import static me.khrystal.threesome.Constants.TAG;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/17
 * update time:
 * email: 723526676@qq.com
 */

public class AppUtil {

    public static String getVersionName(Context context) {
        PackageInfo pInfo;
        try {
            pInfo = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return "";
    }
}
