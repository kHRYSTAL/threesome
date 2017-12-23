package me.khrystal.threesomeandroid.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gyf.barlibrary.ImmersionBar;

import me.khrystal.threesomeandroid.R;
import me.khrystal.threesomeandroid.widget.titlebar.OnTitleClickListener;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBar;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;
import me.khrystal.threesomeandroid.widget.titlebar.TitleType;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/23
 * update time:
 * email: 723526676@qq.com
 */

public abstract class WebViewBaseActivity extends BaseActivity implements OnTitleClickListener {

    protected LinearLayout rootLayout;
    protected RelativeLayout layoutForInfo;

    private TitleBarProxy titleBar;
    private DefaultTitleBarClickListener defaultTitleBarListener;

    public TitleBarProxy getTitleBar() {
        if (titleBar == null) {
            initTitleBar();
        }
        return titleBar;
    }

    public LinearLayout getRootLayout() {
        return rootLayout;
    }

    @Override
    public void onContinueCreate(Bundle savedInstanceState) {
        super.onContinueCreate(savedInstanceState);
        buildFragView();
    }

    /**
     * setup TitleBar according {@link TitleType}, and place a fragment cotainer
     * with id R.id.frag_container
     */
    @SuppressLint("InflateParams")
    private void buildFragView() {

        int customLayoutId = this.layResId();
        if (customLayoutId == 0) {
            View viewTitle = null;
            switch (titleType()) {
                case TitleType.TITLE_LAYOUT:
                    viewTitle = inflater
                            .inflate(R.layout.titlebar_with_image, null);
                    break;
            }

            layoutForInfo = new RelativeLayout(this);
            layoutForInfo.setId(R.id.frag_container_rl);

            rootLayout = new LinearLayout(this);
            rootLayout.setId(R.id.frag_container);
            rootLayout.setOrientation(LinearLayout.VERTICAL);

            // at top add statusView container if hide statusBar show this view
            RelativeLayout statusView = new RelativeLayout(this);
            statusView.setId(R.id.status_bar);
            RelativeLayout.LayoutParams sVParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getResources()
                    .getDimensionPixelSize(R.dimen.status_bar_height)
            );
            sVParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            statusView.setLayoutParams(sVParams);
            rootLayout.addView(statusView);
            statusView.setVisibility(View.GONE);


            if (viewTitle != null) {
                viewTitle.setId(R.id.custom_titile);
                RelativeLayout.LayoutParams vtParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, getResources()
                        .getDimensionPixelSize(R.dimen.title_height));
                vtParams.addRule(RelativeLayout.BELOW, R.id.status_bar);
                rootLayout.addView(viewTitle, vtParams);
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            if (viewTitle != null) {
                params.addRule(RelativeLayout.BELOW, R.id.custom_titile);
            }
            layoutForInfo.addView(rootLayout, params);

            this.setContentView(layoutForInfo, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            this.setContentView(customLayoutId);
        }
        initTitleBar();
    }

    private void initTitleBar() {
        titleBar = new TitleBarProxy();
        defaultTitleBarListener = new DefaultTitleBarClickListener(this);
        titleBar.configTitle(this.getWindow().getDecorView(), titleType(), this);
    }

    @Override
    public void onTitleClicked(View view, int tagId) {
        try {
            switch (tagId) {
                case TitleBarProxy.TAG_BACK:
                    defaultTitleBarListener.onBack();
                    break;
                case TitleBar.TAG_TITLE_TXT: {
                    defaultTitleBarListener.onTitle();
                    break;
                }
                default:
                    break;
            }
        } catch (final Throwable e) {}
    }

    protected int layResId() {
        return 0;
    }

    @Override
    public void updateStatusBarColor(int resId) {
        View statusBar = findViewById(R.id.status_bar);
        statusBar.setBackgroundResource(resId);
        statusBar.setVisibility(View.VISIBLE);
        ImmersionBar.with(this).keyboardEnable(true).init();
    }

    @Override
    public void updateTitleBarColor(int resId) {
        titleBar.getRootView().setBackgroundResource(resId);
        titleBar.hideBottomLine();
    }

    @Override
    public void updateTitleBarAndStatusBar(int resId) {
        updateStatusBarColor(resId);
        updateTitleBarColor(resId);
    }

    @Override
    protected int titleType() {
        return TitleType.TITLE_NONE;
    }

    public class DefaultTitleBarClickListener implements OnTitleClickListener {

        private Activity activity;

        public DefaultTitleBarClickListener(Activity activity) {
            this.activity = activity;
        }

        public void onBack() {
            activity.onBackPressed();
        }


        public void onTitle() {
            // TODO: 17/12/23  scroll to top
        }

        @Override
        public void onTitleClicked(View view, int tagId) {
            try {
                switch (tagId) {
                    case TitleBarProxy.TAG_BACK:
                        onBack();
                        break;
                    case TitleBar.TAG_TITLE_TXT: {
                        onTitle();
                        break;
                    }
                    default:
                        break;
                }
            } catch (final Throwable e) {
            }
        }
    }
}

