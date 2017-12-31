package me.khrystal.threesomeandroid.threesomeapi.titlebar;

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
@Deprecated
public class BackgroundResTask extends BaseThreesomeTask {
    TitleBarProxy titleBarProxy;

    public BackgroundResTask(TitleBarProxy titleBarProxy) {
        this.titleBarProxy = titleBarProxy;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.BACKGROUNDRESOURCE.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        int resId = getImageIdByType(request.param.get("imageType").toString());
        titleBarProxy.getRootView().setBackgroundResource(resId);
        TitleConfig config = new TitleConfig(TitleConfig.TYPE_IMG, resId);
        titleBarProxy.setTitleBackgroundConfig(config);
        return null;
    }

    @Override
    public void destroy() {

    }

    public int getImageIdByType(String imageType) {
        // TODO: 17/12/31 get local img by type
        return 0;
    }
}
