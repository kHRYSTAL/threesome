package me.khrystal.threesome.executor;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import me.khrystal.threesome.Constants;
import me.khrystal.threesome.IBridgeAdapter;
import me.khrystal.threesome.core.BridgeHandler;
import me.khrystal.threesome.core.CallbackFunction;
import me.khrystal.threesome.dto.ResponseCode;
import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.dto.ThreesomeResponse;
import me.khrystal.threesome.util.gson.GsonHelper;

/**
 * usage: bridge handler implements, thread is not safe 
 *          this handler will control executor request to the js  
 * author: kHRYSTAL
 * create time: 17/12/9
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeHandler implements BridgeHandler {
    
    private Gson gson;
    private ITaskExecutor taskExecutor;

    public ThreesomeHandler(ITaskExecutor taskExecutor) {
        gson = GsonHelper.GetCommonGson();
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void handler(String data, CallbackFunction function, IBridgeAdapter bridgeAdapter) {
        data = Uri.decode(data);
        Log.d(Constants.TAG, "start handle: " + data);
        if (function == null) {
            // logic may not be run this
            Log.e(Constants.TAG, "call back is null");
            return;
        }

        ThreesomeRequest request;
        ThreesomeResponse response;

        try {
            request = gson.fromJson(data, ThreesomeRequest.class);
        } catch (Exception ex) {
            // parse exception
            response = new ThreesomeResponse();
            response.code = ResponseCode.ERROR_PARAM;
            response.errorMsg = ex.getLocalizedMessage();
            function.onCallback(response);
            return;
        }
        // argument is null
        if (request == null) {
            response = new ThreesomeResponse();
            response.code = ResponseCode.ERROR_PARAM;
            response.errorMsg = "arguments is null";
            function.onCallback(response);
            return;
        }
        // handler request
        taskExecutor.handleRequest(request, function, bridgeAdapter);
    }
}
