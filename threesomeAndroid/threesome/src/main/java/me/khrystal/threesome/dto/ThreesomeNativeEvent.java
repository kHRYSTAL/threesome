package me.khrystal.threesome.dto;

import com.google.gson.annotations.SerializedName;

import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeNativeEvent extends Dto {

    @SerializedName("type")
    public String eventKey;
    @SerializedName("code")
    public Integer code;

    @Override
    public String toString() {
        return GsonHelper.GetCommonGson().toJson(this);
    }
}
