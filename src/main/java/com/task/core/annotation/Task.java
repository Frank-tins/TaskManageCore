package com.task.core.annotation;

import com.task.core.enums.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明任务模型
 * @author Frank
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Task {
    /**
     * 任务名称
     * @return
     */
    String value() default "";

    /**
     * 任务码 (唯一)
     * @return
     */
    String code() default "";

    /**
     * 任务描述
     * @return
     */
    String describe() default "";

    /**
     * 任务功率
     * @return
     */
    int threadNumber() default 100;

    /**
     * 是否启用
     * @return
     */
    boolean isEnable() default true;

    /**
     * 数据类型
     * @return
     */
    DataType dataType () default DataType.NOT_DATA;

    /**
     * 数据指向表达式,
     *      mapper[class(method)]
     *      bean[class(method or filed)]
     * @return
     */
    String dataExp() default "";

}
