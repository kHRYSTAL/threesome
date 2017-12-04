package me.khrystal.threesome.core;

import me.khrystal.threesome.IBridgeAdapter;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public interface BridgeHandler {
    void handler(String data, CallbackFunction function, IBridgeAdapter bridgeAdapter);
}
