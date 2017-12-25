package me.khrystal.threesomeandroid.widget.titlebar;

import java.io.Serializable;

/**
 * usage: use threesome previous load set background or color
 * author: kHRYSTAL
 * create time: 17/12/22
 * update time:
 * email: 723526676@qq.com
 */

public class TitleConfig implements Serializable {
    public static final int TYPE_COLOR = 1;
    public static final int TYPE_IMG = 2;

    private int type;
    private int bg;

    public TitleConfig(int type, int bg) {
        this.type = type;
        this.bg = bg;
    }

    public int getType() {
        return type;
    }

    public int getBg() {
        return bg;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }
}
