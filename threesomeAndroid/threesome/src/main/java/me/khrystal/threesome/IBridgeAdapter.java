package me.khrystal.threesome;

import me.khrystal.threesome.core.BridgeWebViewClient;
import me.khrystal.threesome.core.WebViewJavascriptBridge;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public interface IBridgeAdapter extends WebViewJavascriptBridge {
    void setClient(BridgeWebViewClient client);
}
