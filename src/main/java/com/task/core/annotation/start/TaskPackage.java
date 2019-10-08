package com.task.core.annotation.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
