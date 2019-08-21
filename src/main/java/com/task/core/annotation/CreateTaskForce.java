package com.task.core.annotation;

import java.lang.annotation.*;

/**
 * 创建任务组
 * @author Frank
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateTaskForce {
//    /**
//     * 是否移交监管器
//     * @return
//     */
//    boolean value() default true;

    /**
     * 是否为单例
     * @return
     */
    boolean single() default false;

    /**
     * 任务码
     * @return
     */
    String code() default "";

}
