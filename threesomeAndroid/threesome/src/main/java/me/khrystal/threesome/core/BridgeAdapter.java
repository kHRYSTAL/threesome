package me.khrystal.threesome.core;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
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

/**
 * usage: webView safe bridge adapter
 * author: kHRYSTAL
 * create time: 16/12/13
 * update time:
 * email: 723526676@qq.com
 */

@SuppressLint("SetJavaScriptEnabled")
public class BridgeAdapter implements IBridgeAdapter {

    public static final String toLoadJs = "WebViewJavascriptBridge.js";
    // 存在两种情况 1.网页端返回的 2.native端直接调用的
    Map<String, CallbackFunction> responseCallbacks = new HashMap<>();
    Map<String, BridgeHandler> messageHandlers = new HashMap<>();
    BridgeHandler defaultHandler = new DefaultHandler();
    private Gson gson = new Gson();

    private WebView mWebView;
    private BridgeWebViewClient bridgeClient;
    // 消息唯一id 自增
    private long uniqueId = 0;

    // 消息队列
    private List<Message> startupMessage = new ArrayList<>();

    public BridgeAdapter(WebView webView) {
        if (webView == null)
            throw new NullPointerException("WebView must not be null!");
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


    /**
     * 通过 与web端定义的schema 处理默认返回的数据
     * 只处理 yy://return/function/data
     * 获取其中的数据 并回调给native端onCallback方法进行后续处理
     *
     * @param url
     */
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
     * native端发送消息的封装
     *
     * @param handlerName
     * @param data
     * @param responseCallback
     */
    private void doSend(String handlerName, Object data, CallbackFunction responseCallback) {
        Message m = new Message();
        m.setData(data);

        if (responseCallback != null) {
            String callbackStr = String.format(BridgeUtil.CALLBACK_ID_FORMAT, ++uniqueId +
                    (BridgeUtil.UNDERLINE_STR + SystemClock.currentThreadTimeMillis()));
            responseCallbacks.put(callbackStr, responseCallback);
            m.setCallbackId(callbackStr);
        }

        if (!TextUtils.isEmpty(handlerName)) {
            m.setHandlerName(handlerName);
        }
        // 将消息加入队列
        queueMessage(m);
    }

    /**
     * 将消息加入队列
     *
     * @param m
     */
    private void queueMessage(Message m) {
        if (startupMessage != null) {
            startupMessage.add(m);
        } else {
            dispatchMessage(m);
        }
    }

    /**
     * 向web端分发消息
     *
     * @param m
     */
    void dispatchMessage(Message m) {
        String messageJson = m.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(BridgeUtil.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            mWebView.loadUrl(javascriptCommand);
        }
    }

    /**
     * @param jsUrl
     * @param returnCallback
     */
    public void loadUrl(String jsUrl, CallbackFunction returnCallback) {
        mWebView.loadUrl(jsUrl);
        responseCallbacks.put(BridgeUtil.parseFunctionName(jsUrl), returnCallback);
    }

    /**
     * register handler,so that javascript can call it
     *
     * @param handlerName
     * @param handler
     */
    @Override
    public void registerHandler(String handlerName, BridgeHandler handler) {
        if (handler != null) {
            messageHandlers.put(handlerName, handler);
            Log.e(Constants.TAG, "register:" + handlerName);
        }
    }

    void flushMessageQueue() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loadUrl(BridgeUtil.JS_FETCH_QUEUE_FROM_JAVA, new CallbackFunction() {

                @Override
                public void onCallback(Object data) {
                    // deserializeMessage
                    List<Message> list = null;
                    Log.e(Constants.TAG, "flush");
                    try {
                        list = gson.fromJson(data.toString(), new TypeToken<List<Message>>() {
                        }.getType());
                    } catch (Exception e) {
                        //todo Notice if callback data is null or not json will return here
                        e.printStackTrace();
                        return;
                    }
                    if (list == null || list.size() == 0) {
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        Message m = list.get(i);
                        String responseId = m.getResponseId();
                        // 是否是response
                        if (!TextUtils.isEmpty(responseId)) {
                            CallbackFunction function = responseCallbacks.get(responseId);
                            function.onCallback(m.getData());
                            responseCallbacks.remove(responseId);
                        } else {
                            CallbackFunction responseFunction = null;
                            // if had callbackId
                            final String callbackId = m.getCallbackId();
                            if (!TextUtils.isEmpty(callbackId)) {
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
                                        //TODO log
                                        Log.e(Constants.TAG, "response: " + data);
                                    }
                                };
                            }
                            BridgeHandler handler;
                            if (!TextUtils.isEmpty(m.getHandlerName())) {
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

    /**
     * call javascript registered handler
     *
     * @param handlerName
     * @param data
     * @param callBack
     */
    @Override
    public void callHandler(String handlerName, Object data, CallbackFunction callBack) {
        doSend(handlerName, data, callBack);
    }

    @Override
    public void setClient(BridgeWebViewClient client) {
        if (mWebView != null) {
            Log.e(BridgeAdapter.class.getSimpleName(), "setCustomClient");
            client.setAdapter(this);
            this.mWebView.setWebViewClient(client);
        }
    }


    public void setDefaultHandler(BridgeHandler handler) {
        this.defaultHandler = handler;
    }

    public List<Message> getStartupMessage() {
        return startupMessage;
    }

    public void setStartupMessage(List<Message> startupMessage) {
        this.startupMessage = startupMessage;
    }
}
