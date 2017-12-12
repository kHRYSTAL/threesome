package me.khrystal.threesome;

import me.khrystal.threesome.core.BridgeWebViewClient;
import me.khrystal.threesome.core.CallbackFunction;
import me.khrystal.threesome.executor.ITask;

/**
 * usage: facade interface
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public interface IThreesomeFacade {

    void setClient(BridgeWebViewClient client);

    void registerTask(ITask task);

    void onDestroy();

    void callHandler(String handlerName, String data, CallbackFunction callback);


}
