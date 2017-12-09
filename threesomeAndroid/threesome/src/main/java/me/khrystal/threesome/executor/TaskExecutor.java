package me.khrystal.threesome.executor;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import me.khrystal.threesome.Constants;
import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.core.CallbackFunction;
import me.khrystal.threesome.dto.ResponseCode;
import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.dto.ThreesomeResponse;

/**
 * usage: common receive bridge message, then dispatch to designated task
 * author: kHRYSTAL
 * create time: 17/12/9
 * update time:
 * email: 723526676@qq.com
 */

public class TaskExecutor implements ITaskExecutor {

    private HashMap<String, ITask> tasks = new HashMap<>();
    private ITask defaultTask;

    private TaskExecutor() {
        defaultTask = new NullTask();
    }

    /**
     * register task to tasks
     */
    @Override
    public void registerTask(ITask task) {
        if (task != null && task.taskId() != null) {
            if (tasks == null)
                tasks = new HashMap<>();
            tasks.put(task.taskId(), task);
        }
    }

    @Override
    public void handleRequest(ThreesomeRequest request, CallbackFunction responseCallback, IBridgeAdapter bridgeAdapter) {
        ThreesomeResponse response = new ThreesomeResponse();
        ITask task = getTask(request.taskId);
        if (task == null) {
            response.code = ResponseCode.ERROR_TASK_NOT_EXIST;
            response.tag = request.tag;
            response.errorMsg = "Task not exist!";
            responseCallback.onCallback(response);
            return;
        }
        try {
            Map<String, Object> result = task.execute(request);
            response.code = ResponseCode.OK;
            response.param = result;
            response.tag = request.tag;
            responseCallback.onCallback(response);
        } catch (Exception e) {
            response.code = ResponseCode.ERROR_INTERNAL;
            response.errorMsg = e.getLocalizedMessage();
            Log.e(Constants.TAG, e.getMessage(), e);
            responseCallback.onCallback(response);
        }
    }

    @Override
    public void destroyTasks() {
        for (ITask task : tasks.values()) {
            task.destroy();
        }
    }

    private ITask getTask(String taskId) {
        if (tasks != null && tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        }
        return defaultTask;
    }

    private void setDefaultTask(ITask task) {
        defaultTask = task;
    }

    public static class TaskExecutorFactory {
        public static ITaskExecutor create() {
            ITaskExecutor executor = new TaskExecutor();
            return executor;
        }
    }
}
