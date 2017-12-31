package me.khrystal.threesomeandroid.threesomeapi.titlebar;

import android.graphics.Color;

import java.util.Map;

import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;
import me.khrystal.threesomeandroid.widget.titlebar.TitleConfig;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/31
 * update time:
 * email: 723526676@qq.com
 */

public class BackgroundColorTask extends BaseThreesomeTask {
    TitleBarProxy titleBarProxy;

    public BackgroundColorTask(TitleBarProxy titleBarProxy) {
        this.titleBarProxy = titleBarProxy;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.BACKGROUNDCOLOR.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        int color = Color.parseColor(request.param.get("color").toString().replace("0x", "#"));
        titleBarProxy.getRootView().setBackgroundColor(color);
        TitleConfig config = new TitleConfig(TitleConfig.TYPE_COLOR, color);
        titleBarProxy.hideBottomLine();
        titleBarProxy.setTitleBackgroundConfig(config);
        return null;
    }

    @Override
    public void destroy() {

    }
}
