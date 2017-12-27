package me.khrystal.threesomeandroid.threesomeapi;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import java.util.Map;

import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;
import me.khrystal.threesomeandroid.threesome.ThreesomeActivity;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;
import me.khrystal.threesomeandroid.widget.titlebar.TitleConfig;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/26
 * update time:
 * email: 723526676@qq.com
 */

public class NavigateToTask extends BaseThreesomeTask {

    private WebView webView;
    private TitleBarProxy titleBarProxy;

    public NavigateToTask(WebView webView, TitleBarProxy titleBarProxy) {
        this.webView = webView;
        this.titleBarProxy = titleBarProxy;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.OPENGROUP.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        String url = request.param.get("url").toString();
        Log.e("AAAA", url);
        if (webView != null) {
            Context context = webView.getContext();
            TitleConfig config = titleBarProxy.getTitleBackgroundConfig();
            ThreesomeActivity.launch(context, url, null, config);
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}