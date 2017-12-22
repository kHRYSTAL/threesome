package me.khrystal.threesomeandroid.widget.titlebar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import me.khrystal.threesomeandroid.R;
import me.khrystal.threesomeandroid.utils.DensityUtil;

/**
 * usage: quickly create titlebar of text and image
 * author: kHRYSTAL
 * create time: 17/12/22
 * update time:
 * email: 723526676@qq.com
 */

public class TitleCreator {

    private static TitleCreator instance;

    public static TitleCreator Instance() {
        if (instance == null) {
            synchronized (TitleCreator.class) {
                if (instance == null) {
                    instance = new TitleCreator();
                }
            }
        }
        return instance;
    }

    private TitleCreator() {}

    public TextView createTextButton(Context context, String text) {
        return createTextButton(context, text, -1);
    }

    public TextView createTextButton(Context context, String text, int color) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        if (color != -1) {
            textView.setTextColor(context.getResources().getColor(color));
        } else {
            textView.setTextColor(Color.parseColor("#F2F2F2"));
        }
        DensityUtil.setTextSize(textView, R.dimen.app_title_btn_text_size);
        return textView;
    }

    public TextView createTextButton(Context context, String text, ColorStateList color) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextColor(color);
        DensityUtil.setTextSize(textView, R.dimen.app_title_btn_text_size);
        return textView;
    }

    public ImageView createImageButton(Context context, int resId) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }
}
