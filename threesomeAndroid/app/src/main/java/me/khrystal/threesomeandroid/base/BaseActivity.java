package me.khrystal.threesomeandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/23
 * update time:
 * email: 723526676@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String logTag;
    protected Handler handler = new Handler();
    protected LayoutInflater inflater;
    private boolean isStopped = false;
    private boolean saveInstanceEnable = false;

    public boolean shouldContinueCreate(Bundle savedInstanceState) {
        return true;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        this.logTag = this.getClass().getSimpleName();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        if (shouldContinueCreate(savedInstanceState)) {
            onContinueCreate(savedInstanceState);
        } else {
            finish();
        }
    }

    @CallSuper
    public void onContinueCreate(Bundle savedInstanceState) {
        inflater = LayoutInflater.from(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        isStopped = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        isStopped = true;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setSaveInstanceEnable(boolean state) {
        this.saveInstanceEnable = state;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (saveInstanceEnable) {
            super.onSaveInstanceState(outState);
        }
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void finishSelf() {
        finish();
    }

    public String getPageName() {
        return null;
    }

    /**
     * TITLE_NONE, TITLE_CUS, TITLE_LAYOUT
     */
    protected abstract int titleType();

    public abstract void updateStatusBarColor(int resId);

    public abstract void updateTitleBarColor(int resId);

    public abstract void updateTitleBarAndStatusBar(int resId);
}
