package me.khrystal.threesome.util;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.equals("");
    }

    public static boolean isEquals(String s1, String s2) {
        if (StringUtil.isNullOrEmpty(s1)) {
            if (StringUtil.isNullOrEmpty(s2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return s1.equals(s2);
        }
    }

}
