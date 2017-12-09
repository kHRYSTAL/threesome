package me.khrystal.threesome.executor;

import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.core.CallbackFunction;
import me.khrystal.threesome.dto.ThreesomeRequest;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/9
 * update time:
 * email: 723526676@qq.com
 */

public interface ITaskExecutor {
    void registerTask(ITask task);

    void handleRequest(ThreesomeRequest request, CallbackFunction responseCallback, IBridgeAdapter bridgeAdapter);

    void destroyTasks();
}
