package me.khrystal.threesomeandroid.threesomeapi;

import android.webkit.WebView;

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

public class RedirectBackTask extends BaseThreesomeTask {

    private WebView webView;
    private IGroupListener groupListener;

    public RedirectBackTask(WebView webView, IGroupListener groupListener) {
        this.webView = webView;
        this.groupListener = groupListener;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.CLOSECURRENTPAGE.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            closeSelf();
        }
        return null;
    }

    @Override
    public void destroy() {

    }

    private void closeSelf() {
        if (groupListener != null) {
            groupListener.onCloseGroup();
        }
    }
}
