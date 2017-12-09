package me.khrystal.threesome.executor;

import com.google.gson.Gson;

import me.khrystal.threesome.Constants;
import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage: task base
 * author: kHRYSTAL
 * create time: 17/12/9
 * update time:
 * email: 723526676@qq.com
 */

public abstract class BaseThreesomeTask implements ITask {

    protected static final String TAG = Constants.TAG;
    private IBridgeAdapter bridgeAdapter;
    private Gson gson = GsonHelper.GetCommonGson();

    @Override
    public void setBridgeAdapter(IBridgeAdapter bridgeAdapter) {
        this.bridgeAdapter = bridgeAdapter;
    }

    @Override
    public IBridgeAdapter getBridgeAdapter() {
        return bridgeAdapter;
    }

    protected Gson getGson() {
        return gson;
    }
}
