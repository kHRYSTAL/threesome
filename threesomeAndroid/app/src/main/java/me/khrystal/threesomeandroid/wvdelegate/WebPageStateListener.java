package me.khrystal.threesomeandroid.wvdelegate;

/**
 * usage: WebPageStateListener
 * author: kHRYSTAL
 * create time: 17/12/15
 * update time:
 * email: 723526676@qq.com
 */

public interface WebPageStateListener {
    // receive title
    void onReceivedTitle(String title);

    // page start
    void onPageStart();

    // page finish
    void onPageFinish();

    // page error
    void onPageError();
}
