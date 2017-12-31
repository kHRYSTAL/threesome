package me.khrystal.threesomeandroid.threesomeapi.titlebar;

import android.widget.ImageView;

import java.util.Map;

import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;
import me.khrystal.threesome.util.StringUtil;
import me.khrystal.threesomeandroid.R;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;

public class BackBtnResTask extends BaseThreesomeTask {

    TitleBarProxy titleBarProxy;

    public BackBtnResTask(TitleBarProxy titleBarProxy) {
        this.titleBarProxy = titleBarProxy;
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.BACKBUTTONRESOURCE.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        int resId = getImageIdByType(request.param.get("navBtnType").toString());
        ImageView imageView = (ImageView) titleBarProxy.getButton(TitleBarProxy.TAG_BACK);
        imageView.setImageResource(resId);
        return null;
    }

    @Override
    public void destroy() {

    }

    public int getImageIdByType(String imageType) {

        if (StringUtil.isEquals(imageType, "navBackWhite")) {
            return R.drawable.sel_btn_back_white;
        } else {
            return R.drawable.sel_btn_back;
        }
    }
}