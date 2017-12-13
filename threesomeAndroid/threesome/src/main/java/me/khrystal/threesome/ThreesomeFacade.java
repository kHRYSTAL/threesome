package me.khrystal.threesome;

import android.util.Log;
import android.webkit.WebView;

import me.khrystal.threesome.core.BridgeAdapter;
import me.khrystal.threesome.core.BridgeWebViewClient;
import me.khrystal.threesome.core.CallbackFunction;
import me.khrystal.threesome.dto.ThreesomeH5Event;
import me.khrystal.threesome.dto.ThreesomeLifeCycleEvent;
import me.khrystal.threesome.dto.ThreesomeNativeEvent;
import me.khrystal.threesome.executor.ITask;
import me.khrystal.threesome.executor.ITaskExecutor;
import me.khrystal.threesome.executor.TaskExecutor;
import me.khrystal.threesome.executor.ThreesomeHandler;
import me.khrystal.threesome.util.RxBus;
import rx.Subscription;
import rx.functions.Action1;

/**
 * usage: facade use rxBus notify message to observable
 * author: kHRYSTAL
 * create time: 17/12/12
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeFacade implements IThreesomeFacade {

    private IBridgeAdapter bridgeAdapter;
    private ITaskExecutor taskExecutor;
    private Subscription nativeEventSubscription;
    private Subscription h5EventSubscription;
    private Subscription lifecycleSubscription;

    public static IThreesomeFacade bridgeWebView(WebView webView) {
        IBridgeAdapter bridge = new BridgeAdapter(webView);
        ITaskExecutor taskExecutor = TaskExecutor.TaskExecutorFactory.create();
        bridge.registerHandler("NativeThreesome", new ThreesomeHandler(taskExecutor));

        ThreesomeFacade threesomeFacade = new ThreesomeFacade();
        threesomeFacade.bridgeAdapter = bridge;
        threesomeFacade.taskExecutor = taskExecutor;
        return threesomeFacade;
    }

    public ThreesomeFacade() {
        nativeEventSubscription = RxBus.getDefault().toObservable(ThreesomeNativeEvent.class)
                .subscribe(new Action1<ThreesomeNativeEvent>() {
                    @Override
                    public void call(ThreesomeNativeEvent threesomeNativeEvent) {
                        Log.d(Constants.TAG, "receive threesome native event" + threesomeNativeEvent.toString());
                        bridgeAdapter.callHandler(Constants.THREESOME_NATIVE_NOTIFICATION, threesomeNativeEvent, null);
                    }
                });
        h5EventSubscription = RxBus.getDefault().toObservable(ThreesomeH5Event.class)
                .subscribe(new Action1<ThreesomeH5Event>() {
                    @Override
                    public void call(ThreesomeH5Event threesomeH5Event) {
                        Log.d(Constants.TAG, "receive threesome h5 event" + threesomeH5Event.toString());
                        bridgeAdapter.callHandler(Constants.THREESOME_H5_NOTIFICATION, threesomeH5Event, null);
                    }
                });
        lifecycleSubscription = RxBus.getDefault().toObservable(ThreesomeLifeCycleEvent.class)
                .subscribe(new Action1<ThreesomeLifeCycleEvent>() {
                    @Override
                    public void call(ThreesomeLifeCycleEvent threesomeLifeCycleEvent) {
                        Log.d(Constants.TAG, "receive threesome lifecycle event" + threesomeLifeCycleEvent.toString());
                        bridgeAdapter.callHandler(Constants.THREESOME_PAGE_LIFECYCLE_NOTIFICATION, threesomeLifeCycleEvent, null);
                    }
                });
    }

    @Override
    public void setClient(BridgeWebViewClient client) {
        bridgeAdapter.setClient(client);
    }

    @Override
    public void registerTask(ITask task) {
        task.setBridgeAdapter(bridgeAdapter);
        taskExecutor.registerTask(task);
    }

    @Override
    public void onDestroy() {
        if (nativeEventSubscription != null) {
            nativeEventSubscription.unsubscribe();
        }
        if (h5EventSubscription != null) {
            h5EventSubscription.unsubscribe();
        }
        if (lifecycleSubscription != null) {
            lifecycleSubscription.unsubscribe();
        }
        taskExecutor.destroyTasks();
    }

    @Override
    public void callHandler(String handlerName, String data, CallbackFunction callback) {
        bridgeAdapter.callHandler(handlerName, data, callback);
    }
}
