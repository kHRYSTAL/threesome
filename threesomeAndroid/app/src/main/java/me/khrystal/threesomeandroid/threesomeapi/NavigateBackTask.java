package me.khrystal.threesomeandroid.threesomeapi;

import java.util.Map;

import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/26
 * update time:
 * email: 723526676@qq.com
 */

public class NavigateBackTask extends BaseThreesomeTask {

    private IGroupListener groupListener;

    public NavigateBackTask(IGroupListener groupListener) {
        this.groupListener = groupListener;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.CLOSECURRENTGROUP.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        if (groupListener != null) {
            groupListener.onCloseGroup();
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
