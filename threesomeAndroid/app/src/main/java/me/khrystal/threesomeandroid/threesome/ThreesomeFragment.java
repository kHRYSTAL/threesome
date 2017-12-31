package me.khrystal.threesomeandroid.threesome;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import me.khrystal.threesome.IThreesomeFacade;
import me.khrystal.threesome.ThreesomeFacade;
import me.khrystal.threesome.util.StringUtil;
import me.khrystal.threesomeandroid.R;
import me.khrystal.threesomeandroid.base.BaseFragment;
import me.khrystal.threesomeandroid.base.WebViewBaseActivity;
import me.khrystal.threesomeandroid.threesomeapi.navigate.IGroupListener;
import me.khrystal.threesomeandroid.threesomeapi.navigate.NavigateBackTask;
import me.khrystal.threesomeandroid.threesomeapi.navigate.NavigateToTask;
import me.khrystal.threesomeandroid.threesomeapi.navigate.RedirectBackTask;
import me.khrystal.threesomeandroid.threesomeapi.navigate.RedirectToTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.BackBtnResTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.BackgroundColorTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.BackgroundResTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.LeftTextColorTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.RightBtnAddTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.TextColorTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.TextSizeTask;
import me.khrystal.threesomeandroid.threesomeapi.titlebar.TitleTextTask;
import me.khrystal.threesomeandroid.widget.titlebar.TitleBarProxy;
import me.khrystal.threesomeandroid.widget.titlebar.TitleConfig;
import me.khrystal.threesomeandroid.wvdelegate.WebPageStateListener;
import me.khrystal.threesomeandroid.wvdelegate.WebViewDelegate;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/12/25
 * update time:
 * email: 723526676@qq.com
 */

public class ThreesomeFragment extends BaseFragment implements WebPageStateListener {

    public static final String PAGE_NAME = "ThreesomePage";

    protected WebViewDelegate webViewDelegate;
    protected IThreesomeFacade threesomeWrapper;
    protected WebView webview;
    protected TitleBarProxy titleBar;
    private String mUrl;

    FrameLayout webVideoContainer;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_threesome, container, false);
        webview = (WebView) root.findViewById(R.id.webView);
        webVideoContainer = (FrameLayout) root.findViewById(R.id.videoView);
        if (getActivity() instanceof WebViewBaseActivity) {
            titleBar = ((WebViewBaseActivity) getActivity()).getTitleBar();
            configTitleBarPre();
        }
        initThreesome();
        return root;
    }

    /**
     * in this sample, when we open or redirect a new page, the page's titleBar need show pre page background before loading
     */
    private void configTitleBarPre() {
        TitleConfig config = (TitleConfig) getActivity().getIntent().getSerializableExtra(ThreesomeActivity.INK_TITLE_CONFIG);
        if (config != null) {
            titleBar.hideBottomLine();
            // when use open group task this config will give to next group
            titleBar.setTitleBackgroundConfig(config);
            if (config.getType() == TitleConfig.TYPE_COLOR) {
                titleBar.getRootView().setBackgroundColor(config.getBg());
            } else if (config.getType() == TitleConfig.TYPE_IMG) {
                titleBar.getRootView().setBackgroundResource(config.getBg());
            }
        }
    }

    protected void initThreesome() {

        webViewDelegate = new WebViewDelegate(webview);
        webViewDelegate.setFrameLayout(webVideoContainer);


        threesomeWrapper = ThreesomeFacade.bridgeWebView(webview);
        threesomeWrapper.setClient(webViewDelegate.getInnerClient());


        //region page task
        threesomeWrapper.registerTask(new NavigateToTask(webview, titleBar));
        threesomeWrapper.registerTask(new RedirectToTask(webview, titleBar));
        threesomeWrapper.registerTask(new NavigateBackTask(new IGroupListener() {
            @Override
            public void onCloseGroup() {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        }));
        threesomeWrapper.registerTask(new RedirectBackTask(webview, new IGroupListener() {
            @Override
            public void onCloseGroup() {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        }));
        //endregion

        //标题栏任务
        if (titleBar != null) {
            threesomeWrapper.registerTask(new BackgroundColorTask(titleBar));
            threesomeWrapper.registerTask(new TextColorTask(titleBar));
            threesomeWrapper.registerTask(new TextSizeTask(titleBar));
            threesomeWrapper.registerTask(new TitleTextTask(titleBar));
            threesomeWrapper.registerTask(new BackgroundResTask(titleBar));
            threesomeWrapper.registerTask(new BackBtnResTask(titleBar));
            threesomeWrapper.registerTask(new LeftTextColorTask(titleBar));
            threesomeWrapper.registerTask(new RightBtnAddTask());
            threesomeWrapper.registerTask(new RightBtnAddTask());
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webViewDelegate.setWebPageStateListener(this);
        configWebView();
    }

    private void configWebView() {
        mUrl = getActivity().getIntent().getStringExtra(ThreesomeActivity.INK_URL);
        String host = getActivity().getIntent().getStringExtra(ThreesomeActivity.INK_COOKIES_HOST);
        String value = getActivity().getIntent().getStringExtra(ThreesomeActivity.INK_COOKIES_VALUE);
        if (StringUtil.isNullOrEmpty(mUrl))
            return;
        boolean fullUrl = mUrl.contains("http://") || mUrl.contains("https://");
        if (!fullUrl) {
            mUrl = "http://" + mUrl;
        }
        webViewDelegate.setLoadFlag(webViewDelegate.FLAG_LOAD_NEW_ACTIVITY);
        loadUrl(mUrl, host, value);
    }

    public void loadUrl(String url, String host, String value) {
        if (!StringUtil.isNullOrEmpty(host) && !StringUtil.isNullOrEmpty(value)) {
            webViewDelegate.loadUrl(url, host, value);
        } else {
            webViewDelegate.loadUrl(url);
        }
    }

    public boolean canGoBack() {
        return webViewDelegate.canGoBack();
    }

    public void goBack() {
        webViewDelegate.goBack();
    }


    @Override
    public String getPageName() {
        return PAGE_NAME;
    }

    /*======================   life cycle   ===============================*/
    @Override
    public void onResume() {
        super.onResume();
        webViewDelegate.onResume();
    }

    @Override
    public void onPause() {
        webViewDelegate.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        threesomeWrapper.onDestroy();
        if (webViewDelegate != null)
            webViewDelegate.onDestroy();
        super.onDestroy();
    }
    /*======================   life cycle   ===============================*/

    @Override
    public void onReceivedTitle(String title) {
        if (getActivity() instanceof ThreesomeActivity) {
            ThreesomeActivity activity = (ThreesomeActivity) getActivity();
            activity.getTitleBar().setTitle(title);
        }
    }

    @Override
    public void onPageStart() {

    }

    @Override
    public void onPageFinish() {

    }

    @Override
    public void onPageError() {

    }

    @Override
    public void onVideoFullScreen() {
        titleBar.getRootView().setVisibility(View.GONE);
    }

    @Override
    public void exitVideoFullScreen() {
        titleBar.getRootView().setVisibility(View.VISIBLE);
    }
}
