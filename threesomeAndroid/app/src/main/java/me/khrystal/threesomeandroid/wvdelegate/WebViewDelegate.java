package me.khrystal.threesomeandroid.wvdelegate;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import java.util.concurrent.TimeUnit;

import me.khrystal.threesome.core.BridgeWebViewClient;
import me.khrystal.threesome.util.StringUtil;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * usage: delegate of webview, use threesome
 * author: kHRYSTAL
 * create time: 17/12/15
 * update time:
 * email: 723526676@qq.com
 */

public class WebViewDelegate {

    private static final String TAG = WebViewDelegate.class.getSimpleName();

    public static final int FLAG_LOAD_NEW_ACTIVITY = 1;
    public static final int FLAG_LOAD_CUR_ACTIVITY = 2;

    private static final String ENCODE = "UTF-8";

    // error url 地址
    private String errorUrl = "file:///android_asset/404.html";

    private WebView webView;

    private WebPageStateListener webListener;
    private WebPageInterceptListener interceptListener;
    private Subscription loadUriDelayOb;

    // default load type = open group
    private int loadFlag = FLAG_LOAD_NEW_ACTIVITY;

    private Context context;
    private SimpleWebViewClient webViewClient;

    private String currentUrl;
    private String host;
    private String cookies;

    public WebViewDelegate(WebView webView) {
        this.webView = webView;
        this.context = webView.getContext();
        configWebView();
    }

    private void configWebView() {
        // TODO: 17/12/15  
    }

    //region public method
    public BridgeWebViewClient getInnerClient() {
        return webViewClient;
    }

    public void setWebPageStateListener(WebPageStateListener listener) {
        this.webListener = listener;
    }

    public void setWebPageInterceptListener(WebPageInterceptListener listener) {
        this.interceptListener = listener;
    }

    public void setWebViewCacheEnable(boolean cacheEnable) {
        webView.getSettings().setAppCacheEnabled(cacheEnable);
    }

    public void setWebViewCacheMode(int cacheMode) {
        webView.getSettings().setCacheMode(cacheMode);
    }

    public void setLoadFlag(int loadFlag) {
        this.loadFlag = loadFlag;
    }

    public void setErrorUrl(String errorUrl) {
        if (!StringUtil.isNullOrEmpty(errorUrl)) {
            this.errorUrl = errorUrl;
        }

    }

    /**
     * if has own url need use cookies can use {@link WebViewDelegate#loadUrl(String)}
     * @param url
     */
    public void loadUrl(String url) {
        if (url.startsWith("http"))
            currentUrl = url; // when use 404.html, do not record
        webView.loadUrl(url);
    }

    public void loadUrl(final String url, final String host, final String cookie) {
        if (url.startsWith("http"))
            currentUrl = url; // when use 404.html, do not record
        this.host = host;
        this.cookies = cookie;
        ValueCallback<Object> callback = new ValueCallback<Object>() {
            @Override
            public void onReceiveValue(Object o) {
                try {
                    // when receive callback, activity maybe destroy so add try-catch
                    setCookies(host, cookie);
                    webView.loadUrl(url);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        };
        removeCookies(callback);
    }

    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void goBack() {
        webView.goBack();
    }
    //endregion

    //region lifecycle
    public void onResume() {
        webView.resumeTimers();
    }

    public void onPause() {
        webView.pauseTimers();
    }

    public void onDestroy() {
        if (webView != null) {
            webView.loadUrl("about:blank");
            ViewGroup vg = (ViewGroup) webView.getParent();
            webView.clearCache(true);
            webView.clearHistory();;
            if (vg != null) {
                vg.removeView(webView);
            }
            webView.destroy();
        }
    }
    //endregion

    private void setCookies(String host, String value) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(host, value);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.createInstance(context.getApplicationContext());
            CookieSyncManager.getInstance().sync();
        }
    }
    
    private void removeCookies(final ValueCallback<Object> callback) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    callback.onReceiveValue(null);
                }
            });
        } else {
            // previous 5.0, cause removeSessionCookies is async, need delay to remove
            cookieManager.removeSessionCookie();;
            if (loadUriDelayOb != null && (!loadUriDelayOb.isUnsubscribed())) {
                loadUriDelayOb.unsubscribe();
            }
            loadUriDelayOb = Observable.timer(500, TimeUnit.MILLISECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            callback.onReceiveValue(null);
                            loadUriDelayOb = null;
                        }
                    });
        }
    }

    final class SimpleWebViewClient extends BridgeWebViewClient {

        private String mOverrideUrl;

        String getOverrideUrl() {
            return mOverrideUrl;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (webListener != null) {
                webListener.onReceivedTitle("");
                webListener.onPageStart();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            try {
                view.stopLoading();
                view.clearView();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            view.loadUrl(errorUrl);
            if (webListener != null) {
                webListener.onPageError();
                webListener.onPageFinish();
            }
        }

        @Override
        public void onPageFinish(WebView view, String url) {
            if (webListener != null) {
                webListener.onReceivedTitle(view.getTitle());
                webListener.onPageFinish();
            }
        }

        @Override
        public boolean shouldOverrideUrl(WebView view, String url) {
            if (StringUtil.isNullOrEmpty(url))
                return false;
            // TODO: 17/12/16

            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse response = super.shouldInterceptRequest(view, url);
            if (interceptListener != null) {
                return interceptListener.shouldInterceptRequest(response, view, url);
            } else {
                return response;
            }
        }
    }
}
