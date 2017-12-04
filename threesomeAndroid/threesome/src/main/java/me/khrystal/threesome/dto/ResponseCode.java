package me.khrystal.threesome.dto;

/**
 * usage: common response code
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */

public interface ResponseCode {
    int OK = 200; // normal
    int ERROR_INTERNAL = 500; // server logic error
    int ERROR_PARAM = 511; // argument Error
    int ERROR_TASK_NOT_EXIST = 520; // task not exist
}
