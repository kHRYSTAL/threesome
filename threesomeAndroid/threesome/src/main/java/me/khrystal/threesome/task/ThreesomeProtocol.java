package me.khrystal.threesome.task;

/**
 * usage: common protocol, if need new protocol, can implements taskid()
 * author: kHRYSTAL
 * create time: 17/12/11
 * update time:
 * email: 723526676@qq.com
 */

public enum ThreesomeProtocol {
    /**
     * navigator
     */
    OPENGROUP("threesome/navigator/openGroup"),
    OPENPAGE("threesome/navigator/openPage"),
    CLOSECURRENTGROUP("threesome/navigator/closeCurGroup"),
    CLOSECURRENTPAGE("threesome/navigator/closeCurPage"),

    /**
     * titlebar
     */
    BACKGROUNDCOLOR("threesome/titleBar/background/color"),
    RIGHTBTN("threesome/titleBar/button"),
    RIGHTBTNDEL("threesome/titleBar/button/delete"),
    TEXTCOLOR("threesome/titleBar/title/textColor"),
    TESTSIZE("threesome/titleBar/title/fontSize"),
    TITLETEXT("threesome/titleBar/title/text"),
    BACKGROUNDRESOURCE("threesome/titleBar/background/image"),
    @Deprecated STATUSBARSTYLE("threesome/statusbar/style"),
    BACKBUTTONRESOURCE("threesome/titleBar/leftbutton"),
    LEFTTEXTCOLOR("threesome/titleBar/leftclosebutton/color");

    private String protocol;

    private ThreesomeProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return this.protocol;
    }
}
