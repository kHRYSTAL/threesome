package me.khrystal.threesome;

import android.webkit.WebView;

import me.khrystal.threesome.core.BridgeAdapter;
import me.khrystal.threesome.core.BridgeWebViewClient;
import me.khrystal.threesome.core.CallbackFunction;
import me.khrystal.threesome.executor.ITask;
import me.khrystal.threesome.executor.ITaskExecutor;
import me.khrystal.threesome.executor.TaskExecutor;
import me.khrystal.threesome.executor.ThreesomeHandler;

/**
 * usage: facade TODO: need rxBus notify message to observable
 * author: kHRYSTAL
 * create time: 17/12/12
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeFacade implements IThreesomeFacade {

    private IBridgeAdapter bridgeAdapter;
    private ITaskExecutor taskExecutor;

    public static IThreesomeFacade bridgeWebView(WebView webView) {
        IBridgeAdapter bridge = new BridgeAdapter(webView);
        ITaskExecutor taskExecutor = TaskExecutor.TaskExecutorFactory.create();
        bridge.registerHandler("NativeThreesome", new ThreesomeHandler(taskExecutor));

        ThreesomeFacade threesomeFacade = new ThreesomeFacade();
        threesomeFacade.bridgeAdapter = bridge;
        threesomeFacade.taskExecutor = taskExecutor;
        return threesomeFacade;
    }

    @Override
    public void setClient(BridgeWebViewClient client) {

    }

    @Override
    public void registerTask(ITask task) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void callHandler(String handlerName, String data, CallbackFunction callback) {

    }
}
