package me.khrystal.threesome.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * usage: base of the request and response
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeReq extends Dto {

    public ThreesomeReq() {
    }

    @SerializedName("taskId")
    public String taskId;

    @SerializedName("param")
    public Map<String, Object> param;

    @SerializedName("tag")
    public String tag;
}
