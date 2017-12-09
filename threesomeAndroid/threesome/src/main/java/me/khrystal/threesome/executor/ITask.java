package me.khrystal.threesome.executor;

import java.util.Map;

import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.dto.ThreesomeRequest;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/9
 * update time:
 * email: 723526676@qq.com
 */

public interface ITask {

    /**
     * taskId all task must implement， return taskId, e.g. threesome/titleBar/backgroundColor
     */
    String taskId();

    /**
     * native op e.g. showToast
     */
    Map<String, Object> execute(ThreesomeRequest request) throws Exception;

    /**
     * when task destory
     */
    void destroy();

    void setBridgeAdapter(IBridgeAdapter bridgeAdapter);

    IBridgeAdapter getBridgeAdapter();
}
