package com.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建任务组
 * @author Frank
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateTaskForce {
    /**
     * 是否移交监管器
     * @return
     */
    boolean value() default true;

    boolean single() default false;
}
