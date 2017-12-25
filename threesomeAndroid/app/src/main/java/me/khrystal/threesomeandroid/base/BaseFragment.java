package me.khrystal.threesomeandroid.base;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/25
 * update time:
 * email: 723526676@qq.com
 */

public abstract class BaseFragment extends Fragment implements FragHandler.HandlerListener {
    protected FragHandler handler = new FragHandler(this);
    protected boolean isViewValid = false;// between view created and destroyed

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    @CallSuper
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.isViewValid = true;
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
    }

    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
    }


    @Override
    @CallSuper
    public void onDestroyView() {
        this.isViewValid = false;
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    public abstract String getPageName();

    public boolean onBackPressed() {
        return false;
    }

    public void finishSelf() {
        this.getActivity().finish();
    }

    public void updateTitleBarColor(int resId) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).updateTitleBarColor(resId);
        }
    }

    public void updateStatusBarColor(int resId) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).updateStatusBarColor(resId);
        }
    }

    public void updateTitleBarAndStatusBarColor(int resId) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).updateTitleBarAndStatusBar(resId);
        }
    }
}
