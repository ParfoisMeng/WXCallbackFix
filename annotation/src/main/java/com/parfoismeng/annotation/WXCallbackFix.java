package com.parfoismeng.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * author : ParfoisMeng
 * time   : 2020-01-07
 * desc   : WXCallbackFix 注解类, 需传入对应新的包名路径.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface WXCallbackFix {
    String value();
}