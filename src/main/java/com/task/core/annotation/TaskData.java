package com.task.core.annotation;

import java.lang.annotation.*;

/**
 * 标注传入方法内用于承载分段数据的字段
 * @author Frank
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskData {
}
