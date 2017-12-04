package me.khrystal.threesome.dto;

import com.google.gson.annotations.SerializedName;

import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage: lifecycle notify html event
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeLifeCycleEvent extends Dto {
    public static final String APPEAR = "appear";
    public static final String DISAPPEAR = "disappear";

    @SerializedName("param")
    public LifecycleParam param;

    public ThreesomeLifeCycleEvent(String lifecycle) {
        LifecycleParam param = new LifecycleParam(lifecycle);
        this.param = param;
    }

    class LifecycleParam extends Dto {

        public LifecycleParam(String type) {
            this.type = type;
        }

        @SerializedName("type")
        public String type;
    }

    @Override
    public String toString() {
        return GsonHelper.GetCommonGson().toJson(this);
    }
}
