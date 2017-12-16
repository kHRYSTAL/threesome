package me.khrystal.threesomeandroid.wvdelegate;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;

/**
 * usage: intercept some file url to use local resource
 * author: kHRYSTAL
 * create time: 17/12/16
 * update time:
 * email: 723526676@qq.com
 */

public interface WebPageInterceptListener {
    WebResourceResponse shouldInterceptRequest(WebResourceResponse superResp, WebView view, String url);
}
