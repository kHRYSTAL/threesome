package me.khrystal.threesome.core;

import android.webkit.WebView;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class DefaultWebViewClient extends BridgeWebViewClient {
    @Override
    public void onPageFinish(WebView view, String url) {
        // do nothing
    }

    @Override
    public boolean shouldOverrideUrl(WebView view, String url) {
        // do nothing
        return false;
    }
}
