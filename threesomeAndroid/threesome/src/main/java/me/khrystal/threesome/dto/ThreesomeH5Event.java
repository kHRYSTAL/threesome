package me.khrystal.threesome.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage: h5 event
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeH5Event extends Dto {
    @SerializedName("taskId")
    public String taskId;

    @SerializedName("param")
    public Map<String, Object> param;

    @SerializedName("tag")
    public String tag;

    @Override
    public String toString() {
        return GsonHelper.GetCommonGson().toJson(this);
    }

    public static ThreesomeH5Event create(ThreesomeRequest request) {
        ThreesomeH5Event threesomeH5Event = new ThreesomeH5Event();
        if (request != null) {
            threesomeH5Event.taskId = request.taskId;
            threesomeH5Event.param = request.param;
            threesomeH5Event.tag = request.tag;
        }
        return threesomeH5Event;
    }
}
