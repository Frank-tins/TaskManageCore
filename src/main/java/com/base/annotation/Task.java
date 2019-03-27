package com.base.annotation;

/**
 * 声明任务模型
 * @author Frank
 */
public @interface Task {

    String value() default "";

    String code() default "";

    String describe() default "";

    int threadNumber() default -1;

    boolean isEnable() default true;

}
