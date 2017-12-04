package me.khrystal.threesome.dto;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeResponse extends ThreesomeReq {
    public ThreesomeResponse() {

    }

    public ThreesomeResponse(ThreesomeReq req) {
        this.taskId = req.taskId;
        this.tag = req.tag;
    }

    @SerializedName("code")
    public int code;

    @SerializedName("errorMsg")
    public String errorMsg;

    /**
     * add key-value
     */
    public void putParam(String key, Object value) {
        if (param == null) {
            param = new HashMap<>();
        }

        param.put(key, value);
    }
}
