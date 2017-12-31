package me.khrystal.threesomeandroid.threesomeapi.titlebar;

import android.graphics.Color;
import android.widget.TextView;

import java.util.Map;

import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;
import me.khrystal.threesomeandroid.threesome.ThreesomeActivity;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/31
 * update time:
 * email: 723526676@qq.com
 */
public class LeftTextColorTask extends BaseThreesomeTask {

    TitleBarProxy titleBarProxy;

    public LeftTextColorTask(TitleBarProxy titleBarProxy) {
        this.titleBarProxy = titleBarProxy;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.LEFTTEXTCOLOR.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        ((TextView) titleBarProxy.getButton(ThreesomeActivity.TAG_CLOSE)).setTextColor(Color.parseColor(request.param.get("color").toString().replace("0x", "#")));
        return null;
    }

    @Override
    public void destroy() {

    }
}