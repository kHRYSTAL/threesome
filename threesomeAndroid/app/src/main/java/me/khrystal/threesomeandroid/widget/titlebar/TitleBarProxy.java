package me.khrystal.threesomeandroid.widget.titlebar;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.khrystal.threesomeandroid.R;
import me.khrystal.threesomeandroid.app.SampleApplication;

/**
 * usage: titlebar proxy
 * author: kHRYSTAL
 * create time: 17/12/22
 * update time:
 * email: 723526676@qq.com
 */

public class TitleBarProxy {

    public static final int TAG_BACK = 601;
    private TitleBar titleBar;
    private TitleConfig config;

    public void configTitle(View root, int titleType, OnTitleClickListener listener) {
        switch (titleType) {
            case TitleType.TITLE_LAYOUT: {
                View view = root.findViewById(R.id.custom_titile);
                if (view == null) {
                    throw new UnsupportedOperationException();
                }
                titleBar = new TitleBar(root, listener);
                titleBar.setBackgroud(R.color.white);
                titleBar.setTextStyle(R.style.titleBar);
                break;
            }
            default:
                break;
        }
    }

    public void addBackButton() {
        if (titleBar != null) {
            View backBtn = TitleCreator.Instance().createImageButton(
                    SampleApplication.CONTEXT, R.drawable.sel_btn_back);
            titleBar.addLeftTitleButton(backBtn, TAG_BACK);
        }
    }

    public void addBackButton(int btnBackResID) {
        if (titleBar != null) {
            View backBtn = TitleCreator.Instance().createImageButton(
                    SampleApplication.CONTEXT, btnBackResID);
            titleBar.addLeftTitleButton(backBtn, TAG_BACK);
        }
    }

    public void setTitle(String title) {
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    public void setTitle(SpannableString title) {
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    public void setTitleTypeFace(Typeface typeFace) {
        if (titleBar != null) {
            titleBar.setTitleTypeFace(typeFace);
        }
    }

    public void setImageTitle(int resId) {
        if (titleBar != null) {
            titleBar.setImageTitle(resId);
        }
    }

    public void setTitleBackground(int resId) {
        if (titleBar != null) {
            titleBar.setBackgroud(resId);
        }
    }

    public void getTitle() {
        if (titleBar != null) {
            titleBar.getTitle();
        }
    }

    public TextView getTitleTextView() {
        if (titleBar != null) {
            return titleBar.getTitleTextView();
        }
        return null;
    }

    public void addLeftTitleButton(View view, int tagId) {
        if (titleBar != null) {
            titleBar.addLeftTitleButton(view, tagId);
        }
    }

    public void addRightTitleButton(View view, int tagId) {
        if (titleBar != null) {
            titleBar.addRightTitleButton(view, tagId);
        }
    }

    public void addRightTitleButton(View view, int tagId,
                                    LinearLayout.LayoutParams param) {
        if (titleBar != null) {
            titleBar.addRightTitleButton(view, tagId, param);
        }
    }

    public void hideTitleButton(int tagId) {
        if (titleBar != null) {
            titleBar.hideTitleButton(tagId);
        }
    }

    public void hideAllButtons() {
        if (titleBar != null) {
            titleBar.hideAllButtons();
        }
    }

    public void hideRightButtons() {
        titleBar.hideRightButtons();
    }

    public void removeRightButtons() {titleBar.removeRightButtons();}

    public void showTitleButton(int tagId) {
        if (titleBar != null) {
            titleBar.showTitleButton(tagId);
        }
    }

    public void enableTitleButton(int tagId) {
        if (titleBar != null) {
            titleBar.enableTitleButton(tagId);
        }
    }

    public void disableTitleButton(int tagId) {
        if (titleBar != null) {
            titleBar.disableTitleButton(tagId);
        }
    }

    public void showBottomLine() {
        if (titleBar != null) {
            titleBar.showBottomLine();
        }
    }

    public void hideBottomLine() {
        if (titleBar != null) {
            titleBar.hideBottomLine();
        }
    }

    public View getRootView() {
        return titleBar.getRootView();
    }

    public View getButton(int tagId) {
        if (titleBar != null)
            return titleBar.getButton(tagId);
        return null;
    }

    public void setTitleColor(int titleColor) {
        titleBar.setTitleColor(titleColor);
    }

    public void setTitleBackgroundConfig(TitleConfig config) {
        this.config = config;
    }

    public TitleConfig getTitleBackgroundConfig() {
        return config;
    }
}
