package me.khrystal.threesomeandroid.threesomeapi.titlebar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import me.khrystal.threesome.dto.Dto;
import me.khrystal.threesome.dto.ThreesomeRequest;
import me.khrystal.threesome.executor.BaseThreesomeTask;
import me.khrystal.threesome.task.ThreesomeProtocol;
import me.khrystal.threesome.util.StringUtil;
import me.khrystal.threesomeandroid.R;
import me.khrystal.threesomeandroid.app.SampleApplication;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;
import me.khrystal.threesomeandroid.widget.titlebar.TitleCreator;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/31
 * update time:
 * email: 723526676@qq.com
 */

public class RightBtnAddTask extends BaseThreesomeTask {

    private TitleBarProxy titleBarProxy;
    private Map<String, Integer> pictures;
    private Gson gson = new Gson();

    public RightBtnAddTask(TitleBarProxy titleBarProxy) {
        this.titleBarProxy = titleBarProxy;
        pictures = new HashMap<>(5);
        pictures.put("navClose", R.drawable.sel_btn_close);
        pictures.put("navBack", R.drawable.sel_btn_back);
        pictures.put("navSearch", R.drawable.sel_btn_search);
        pictures.put("navAdd", R.drawable.sel_btn_add);
        pictures.put("navShare", R.drawable.sel_btn_share);
        pictures.put("navMore", R.drawable.sel_btn_more);
    }

    @Override
    public String taskId() {
        return ThreesomeProtocol.RIGHTBTN.getProtocol();
    }

    @Override
    public Map<String, Object> execute(ThreesomeRequest request) throws Exception {
        final BtnParam btnParam = gson.fromJson(gson.toJson(request.param), BtnParam.class);
        View view = null;
        int viewId = 0;
        if (!StringUtil.isNullOrEmpty(btnParam.txt)) {
            view = TitleCreator.Instance().createTextButton(SampleApplication.CONTEXT, btnParam.txt, dynamicGenerateColorList(btnParam));
            viewId = btnParam.txt.hashCode();
        } else if (!StringUtil.isNullOrEmpty(btnParam.navBtnType)) {
            Integer drawableId = pictures.get(btnParam.navBtnType);
            if (drawableId != null) {
                view = TitleCreator.Instance().createImageButton(SampleApplication.CONTEXT, drawableId);
                viewId = drawableId.hashCode();
            }
        }
        if (view != null) {
            titleBarProxy.hideTitleButton(viewId);
            titleBarProxy.addRightTitleButton(view, viewId);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!StringUtil.isNullOrEmpty(btnParam.handlerName)) {
                        getBridgeAdapter().callHandler(btnParam.handlerName, null, null);
                    }
                }
            });
        }

        return null;
    }

    @Override
    public void destroy() {

    }

    private ColorStateList dynamicGenerateColorList(BtnParam param) {


        try {
            int[][] states = new int[][]{
                    new int[]{android.R.attr.state_enabled}, // enabled
                    new int[]{android.R.attr.state_pressed}  // pressed
            };
            int[] colors = new int[]{
                    Color.parseColor("#CEAD7E"),
                    Color.parseColor("#CEAD7E")
            };
            if (!StringUtil.isNullOrEmpty(param.normalColor)) {
                colors[0] = Color.parseColor(param.normalColor.toString().replace("0x", "#"));
            }

            if (!StringUtil.isNullOrEmpty(param.pressColor)) {
                colors[1] = Color.parseColor(param.pressColor.toString().replace("0x", "#"));
            }
            return new ColorStateList(states, colors);
        } catch (Exception e) {
            int[][] states = new int[][]{
                    new int[]{android.R.attr.state_enabled}, // enabled
                    new int[]{android.R.attr.state_pressed}  // pressed
            };
            int[] colors = new int[]{
                    Color.parseColor("#CEAD7E"),
                    Color.parseColor("#CEAD7E")
            };
            return new ColorStateList(states, colors);
        }
    }

    private static final class BtnParam extends Dto {
        @SerializedName("txt")
        String txt;
        @SerializedName("handlerName")
        String handlerName;
        @SerializedName("navBtnType")
        String navBtnType;
        @SerializedName("color")
        String normalColor;
        @SerializedName("tapColor")
        String pressColor;

    }
}
