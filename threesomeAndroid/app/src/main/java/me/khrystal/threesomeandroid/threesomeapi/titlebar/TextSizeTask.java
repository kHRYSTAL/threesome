package me.khrystal.threesomeandroid.threesomeapi.titlebar;

import java.util.Map;

import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/31
 * update time:
 * email: 723526676@qq.com
 */

public class TextSizeTask extends BaseThreesomeTask {

    TitleBarProxy titleBarProxy;

    public TextSizeTask(TitleBarProxy titleBarProxy) {
        this.titleBarProxy = titleBarProxy;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.TESTSIZE.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        titleBarProxy.getTitleTextView().setTextSize(Integer.parseInt(request.param.get("size").toString()));
        return null;
    }

    @Override
    public void destroy() {

    }
}
