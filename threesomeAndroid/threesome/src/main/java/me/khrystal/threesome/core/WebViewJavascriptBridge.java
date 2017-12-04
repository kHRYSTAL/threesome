package me.khrystal.threesome.core;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public interface WebViewJavascriptBridge {

    void registerHandler(String handlerName, BridgeHandler handler);

    void callHandler(String handlerName, Object data, CallbackFunction callback);
}
