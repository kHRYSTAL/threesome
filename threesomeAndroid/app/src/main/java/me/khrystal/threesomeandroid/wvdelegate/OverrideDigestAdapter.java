package me.khrystal.threesomeandroid.wvdelegate;

import android.webkit.WebView;

import me.khrystal.threesome.core.BridgeWebViewClient;

/**
 * usage: digest url Adapter
 * author: kHRYSTAL
 * create time: 17/12/16
 * update time:
 * email: 723526676@qq.com
 */

public interface OverrideDigestAdapter {
    /**
     * when execute {@link BridgeWebViewClient#shouldOverrideUrl(WebView, String)}
     * can filter or digest special url
     */
    boolean digestUrl(String url);
}
