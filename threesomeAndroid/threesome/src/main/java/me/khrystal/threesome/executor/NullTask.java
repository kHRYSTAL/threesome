package me.khrystal.threesome.executor;

import android.util.Log;

import java.util.Map;

import me.khrystal.threesome.Constants;
import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.dto.ThreesomeRequest;

/**
 * usage: DefaultTask of threesome
 * author: kHRYSTAL
 * create time: 17/12/9
 * update time:
 * email: 723526676@qq.com
 */

public class NullTask implements ITask {
    @Override
    public String taskId() {
        return "";
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        Log.d(Constants.TAG, "this task is default task, because TaskExecutor of tasks is null!");
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setBridgeAdapter(IBridgeAdapter bridgeAdapter) {

    }

    @Override
    public IBridgeAdapter getBridgeAdapter() {
        return null;
    }
}
