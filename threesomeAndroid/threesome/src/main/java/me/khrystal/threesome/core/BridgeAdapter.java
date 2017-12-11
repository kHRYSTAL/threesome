package me.khrystal.threesome.core;

import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.khrystal.threesome.Constants;
import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.util.StringUtil;
import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class BridgeAdapter implements IBridgeAdapter {

    public static final String toLoadJs = "WebViewJavascriptBridge.js";
    Map<String, CallbackFunction> responseCallbacks = new HashMap<>();
    Map<String, BridgeHandler> messageHandlers = new HashMap<>();
    BridgeHandler defaultHandler = new DefaultHandler();
    private Gson gson = GsonHelper.GetCommonGson();

    private WebView mWebView;
    private BridgeWebViewClient bridgeClient;
    // message unique id auto increment
    private long uniqueId = 0;

    // message queue
    private List<Message> startupMessage = new ArrayList<>();

    public BridgeAdapter(WebView webView) {
        if (webView == null) {
            throw new NullPointerException("WebView must not be null!");
        }
        mWebView = webView;
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        bridgeClient = generateDefaultBridgeWebViewClient();
        webView.setWebViewClient(bridgeClient);
    }

    private BridgeWebViewClient generateDefaultBridgeWebViewClient() {
        DefaultWebViewClient defaultWebViewClient = new DefaultWebViewClient();
        defaultWebViewClient.setAdapter(this);
        return defaultWebViewClient;
    }

    void handlerReturnData(String url) {
        String functionName = BridgeUtil.getFunctionFromReturnUrl(url);
        CallbackFunction f = responseCallbacks.get(functionName);
        String data = BridgeUtil.getDataFromReturnUrl(url);
        if (f != null) {
            f.onCallback(data);
            responseCallbacks.remove(functionName);
        }
    }

    /**
     * wrap native to html msg
     */
    private void doSend(String handlerName, Object data, CallbackFunction responseCallback) {
        Message m = new Message();
        m.setData(data);

        if (responseCallback != null) {
            String callbackStr = String.format(BridgeUtil.CALLBACK_ID_FORMAT, ++uniqueId +
                    (BridgeUtil.UNDELINE_STR + SystemClock.currentThreadTimeMillis()));
            responseCallbacks.put(callbackStr, responseCallback);
            m.setCallbackId(callbackStr);
        }

        if (!StringUtil.isNullOrEmpty(handlerName)) {
            m.setHandlerName(handlerName);
        }
        // add message to the queue
        queueMessage(m);
    }

    private void queueMessage(Message m) {
        if (startupMessage != null) {
            startupMessage.add(m);
        } else {
            dispatchMessage(m);
        }
    }

    /**
     * send message to html
     */
    void dispatchMessage(Message m) {
        String messageJson = m.toJson();
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(BridgeUtil.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            mWebView.loadUrl(javascriptCommand);
        }
    }

    void flushMessageQueen() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loadUrl(BridgeUtil.JS_FETCH_QUEUE_FRAM_JAVA, new CallbackFunction() {
                @Override
                public void onCallback(Object data) {
                    // deserializeMessage
                    List<Message> list = null;
                    try {
                        list = gson.fromJson(data.toString(), new TypeToken<List<Message>>() {
                        }.getType());
                    } catch (Exception e) {
                        // notice: if callback data is null or not json will return here
                        Log.e(Constants.TAG, "json parse error!");
                        e.printStackTrace();
                        return;
                    }
                    if (list.isEmpty()) {
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        Message m = list.get(i);
                        String responseId = m.getResponseId();
                        if (!StringUtil.isNullOrEmpty(responseId)) {
                            // is response
                            CallbackFunction function = responseCallbacks.get(responseId);
                            function.onCallback(m.getData());
                            responseCallbacks.remove(responseId);
                        } else {
                            CallbackFunction responseFunction = null;
                            // if had callbackId
                            final String callbackId = m.getCallbackId();
                            if (!StringUtil.isNullOrEmpty(callbackId)) {
                                responseFunction = new CallbackFunction() {
                                    @Override
                                    public void onCallback(Object data) {
                                        Message responseMsg = new Message();
                                        responseMsg.setResponseId(callbackId);
                                        responseMsg.setData(data);
                                        Log.e(Constants.TAG, "response: " + data);
                                        queueMessage(responseMsg);
                                    }
                                };
                            } else {
                                responseFunction = new CallbackFunction() {
                                    @Override
                                    public void onCallback(Object data) {
                                        // do nothing
                                        Log.e(Constants.TAG, "response: " + data);
                                    }
                                };
                            }
                            BridgeHandler handler;
                            if (!StringUtil.isNullOrEmpty(m.getHandlerName())) {
                                handler = messageHandlers.get(m.getHandlerName());
                            } else {
                                handler = defaultHandler;
                            }
                            if (handler != null) {
                                String param = m.getData() == null ? null : gson.toJson(m.getData());
                                handler.handler(param, responseFunction, BridgeAdapter.this);
                            }
                        }
                    }
                }

            });
        }
    }

    public void loadUrl(String jsUrl, CallbackFunction returnCallback) {
        mWebView.loadUrl(jsUrl);
        responseCallbacks.put(BridgeUtil.parseFunctionName(jsUrl), returnCallback);
    }

    @Override
    public void setClient(BridgeWebViewClient client) {
        if (mWebView != null) {
            client.setAdapter(this);
            this.mWebView.setWebViewClient(client);
        }
    }

    @Override
    public void registerHandler(String handlerName, BridgeHandler handler) {
        if (handler != null) {
            messageHandlers.put(handlerName, handler);
            Log.e(Constants.TAG, "register:" + handlerName);
        }
    }

    @Override
    public void callHandler(String handlerName, Object data, CallbackFunction callback) {
        doSend(handlerName, data, callback);
    }

    public List<Message> getStartupMessage() {
        return startupMessage;
    }

    public void setStartupMessage(List<Message> startupMessage) {
        this.startupMessage = startupMessage;
    }
}
