package me.khrystal.threesome.executor;

import com.google.gson.Gson;

import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.core.BridgeHandler;
import me.khrystal.threesome.core.CallbackFunction;
import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage: bridge handler implements, thread is not safe 
 *          this handler will control executor request to the js  
 * author: kHRYSTAL
 * create time: 17/12/9
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeHandler implements BridgeHandler {
    
    private Gson gson;
    private ITaskExecutor taskExecutor;

    public ThreesomeHandler(ITaskExecutor taskExecutor) {
        gson = GsonHelper.GetCommonGson();
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void handler(String data, CallbackFunction function, IBridgeAdapter bridgeAdapter) {
        // TODO: 17/12/9  
    }
}
