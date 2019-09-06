package com.task.core.annotation.start;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 任务包
 *
 * @author Frank
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskPackage {

    @AliasFor(attribute = "packageName")
    String value() default "";

    @AliasFor(attribute = "value")
    String packageName () default "";



}
