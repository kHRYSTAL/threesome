package me.khrystal.threesomeandroid.threesomeapi;

import android.webkit.WebView;

import java.util.Map;

import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/26
 * update time:
 * email: 723526676@qq.com
 */

public class RedirectToTask extends BaseThreesomeTask {

    private TitleBarProxy titleBarProxy;
    private WebView webView;

    public RedirectToTask(WebView webView, TitleBarProxy titleBar) {
        this.titleBarProxy = titleBar;
        this.webView = webView;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.OPENPAGE.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        String url = request.param.get("url").toString();
        if (webView != null) {
            webView.loadUrl(url);
            titleBarProxy.hideRightButtons();
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
