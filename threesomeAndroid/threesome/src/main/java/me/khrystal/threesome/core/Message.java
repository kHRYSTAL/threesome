package me.khrystal.threesome.core;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import me.khrystal.threesome.dto.Dto;
import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class Message extends Dto {

    private String callbackId;
    private String responseId;
    private Object data;
    private String handlerName;

    public String toJson() {
        Gson gson = GsonHelper.GetCommonGson();
        MsgForGson mf = new MsgForGson();
        if (data != null) {
            String dataString = gson.toJson(data);
            try {
                // because webview will auto decode, so need encode twice
                mf.data = URLEncoder.encode(dataString, "UTF-8");
                mf.data = URLEncoder.encode(mf.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        mf.callbackId = callbackId;
        mf.handlerName = handlerName;
        mf.responseId = responseId;
        return gson.toJson(mf);
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    private static final class MsgForGson extends Dto {
        String callbackId;
        String responseId;
        String data;
        String handlerName;
    }
}
