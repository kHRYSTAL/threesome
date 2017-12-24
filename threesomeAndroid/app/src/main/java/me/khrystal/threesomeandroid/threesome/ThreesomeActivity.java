package me.khrystal.threesomeandroid.threesome;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import me.khrystal.threesome.util.StringUtil;
import me.khrystal.threesomeandroid.R;
import me.khrystal.threesomeandroid.base.WebViewBaseActivity;
import me.khrystal.threesomeandroid.widget.titlebar.TitleConfig;
import me.khrystal.threesomeandroid.widget.titlebar.TitleCreator;
import me.khrystal.threesomeandroid.widget.titlebar.TitleType;

/**
 * usage: threesome impl
 * author: kHRYSTAL
 * create time: 17/12/24
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeActivity extends WebViewBaseActivity {

    public static final String INK_URL = "frag_web_url";
    public static final String INK_TITLE = "frag_web_title";
    public static final String INK_TITLE_CONFIG = "frag_web_title_config";
    public static final String INK_COOKIES_HOST = "frag_web_cookies_host";
    public static final String INK_COOKIES_VALUE = "frag_web_cookies_value";

    public static final int TAG_CLOSE = 100;
    private static final int TAG_RIGHT_BTN = 200;


    public static void launch(Context context, String url, String title) {
        Intent intent = new Intent();
        intent.setClass(context, ThreesomeActivity.class);
        intent.putExtra(INK_URL, url);
        intent.putExtra(INK_TITLE, title);
        context.startActivity(intent);
    }

    public static void launch(Context context, String url, String title, TitleConfig config) {
        Intent intent = new Intent();
        intent.setClass(context, ThreesomeActivity.class);
        intent.putExtra(INK_URL, url);
        intent.putExtra(INK_TITLE, title);
        if (config != null)
            intent.putExtra(INK_TITLE_CONFIG, config);
        context.startActivity(intent);
    }

    public static void launch(Context context, String url, String title, String host, String value) {
        Intent intent = new Intent();
        intent.setClass(context, ThreesomeActivity.class);
        intent.putExtra(INK_URL, url);
        intent.putExtra(INK_TITLE, title);
        intent.putExtra(INK_COOKIES_HOST, host);
        intent.putExtra(INK_COOKIES_VALUE, value);
        context.startActivity(intent);
    }

    @Override
    public boolean shouldContinueCreate(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(INK_URL);
        if (StringUtil.isNullOrEmpty(url)) {
            return false;
        }
        return true;
    }

    @Override
    public void onContinueCreate(Bundle savedInstanceState) {
        super.onContinueCreate(savedInstanceState);
        getTitleBar().addBackButton();
        View tvClose = TitleCreator.Instance().createTextButton(this, "关闭", R.color.black);
        getTitleBar().addLeftTitleButton(tvClose, TAG_CLOSE);
        getTitleBar().hideTitleButton(TAG_CLOSE);

        String titleStr = getIntent().getStringExtra(INK_TITLE);
        getTitleBar().setTitle(titleStr);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // TODO: 17/12/24 add fragment
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        // TODO: 17/12/24
    }

    @Override
    protected int titleType() {
        return TitleType.TITLE_LAYOUT;
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    //region threesome lifecycle
    @Override
    protected void onStart() {
        // TODO: 17/12/24
        super.onStart();
    }

    @Override
    protected void onResume() {
        // TODO: 17/12/24
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO: 17/12/24
        super.onPause();

    }
    //endregion
}
