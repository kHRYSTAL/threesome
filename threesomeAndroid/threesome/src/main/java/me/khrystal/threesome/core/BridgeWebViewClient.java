package me.khrystal.threesome.core;

import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import me.khrystal.threesome.Constants;

/**
 * usage: abstract class if you want use jsbridge with your custom webviewClient
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public abstract class BridgeWebViewClient extends WebViewClient {

    private BridgeAdapter mAdapter;

    public void setAdapter(BridgeAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(Constants.TAG, "shouldOverrideUrlLoading: " + url);
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(Constants.TAG, e.getMessage());
        }
        if (url.trim().equals(BridgeUtil.OVERRIDE_LOAD)) {
            if (BridgeAdapter.toLoadJs != null) {
                Log.d(Constants.TAG, "load bridge js");
                BridgeUtil.webViewLoadLocalJs(view, BridgeAdapter.toLoadJs);
            }
            // interceptor
            return true;
        } else if (url.startsWith(BridgeUtil.RETURN_DATA)) {
            Log.d(Constants.TAG, "hybrid return data");
            if (mAdapter != null) {
                mAdapter.handlerReturnData(url);
            }
            // interceptor
            return true;
        } else if (url.startsWith(BridgeUtil.OVERRIDE_SCHEMA)) {
            Log.d(Constants.TAG, "hybrid be called");
            if (mAdapter != null) {
                // if response
                mAdapter.flushMessageQueen();
            }
            return true;
        } else {
            Log.d(Constants.TAG, "other client call: " + url);
            return shouldOverrideUrl(view, url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(Constants.TAG, "onPageFinished");
        if (mAdapter != null) {
            if (mAdapter.getStartupMessage() != null) {
                for (Message m : (List<Message>) mAdapter.getStartupMessage()) {
                    Log.d(Constants.TAG, "dispatch bridge message");
                    mAdapter.dispatchMessage(m);
                }
                mAdapter.setStartupMessage(null);
            }
        }
        onPageFinish(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        Log.d(Constants.TAG, "onReceivedError");
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
        Log.d(Constants.TAG, "onReceivedHttpAuthRequest");
    }

    public abstract void onPageFinish(WebView view, String url);

    public abstract boolean shouldOverrideUrl(WebView view, String url);
}
