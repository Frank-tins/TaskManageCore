package com.task.core.annotation.start;

import com.task.core.start.TaskCoreStart;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 启用TaskManageCore
 * @author Frank
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        TaskCoreStart.class
})
public @interface EnableTaskCore {

    @AliasFor(attribute = "baseTaskPackage")
    TaskPackage[] value() default {};

    /**
     * 线程池配置注解
     * @return
     */
    TaskThreadPool poolAnnotation() default @TaskThreadPool(enable = false);

    /**
     * 任务包注解
     * @return
     */
    @AliasFor(attribute = "value")
    TaskPackage[] baseTaskPackage() default {};




}