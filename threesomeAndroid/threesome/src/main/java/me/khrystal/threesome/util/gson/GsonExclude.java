package me.khrystal.threesome.util.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * usage: exclude field when serialize object
 * author: kHRYSTAL
 * create time: 17/12/4
 * update time:
 * email: 723526676@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GsonExclude {
}
