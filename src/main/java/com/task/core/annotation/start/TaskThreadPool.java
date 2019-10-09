package com.task.core.annotation.start;

import java.lang.annotation.*;

/**
 * 线程池配置类
 * @author Frank
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskThreadPool {

    boolean enable () default true;

    /**
     * 线程池最大大小
     * @return
     */
    int maxPool() default Integer.MAX_VALUE;

    /**
     * 优先级
     * @return
     */
    int priority() default 5;

    /**
     * 线程核心大小
     * @return
     */
    int corePoolSize() default 50;

    /**
     * 队列大小
     * 当设置 @see com.task.core.annotation.start.TaskThreadPool#queueBeanName 中存在正确Bean 时 此项不生效
     * @return
     */
    int queueCapacity() default -1;

    /**
     * 线程前缀
     * @return
     */
    String threadNamePrefix() default "task-manage-core";

    /**
     * 维持时间
     * @return
     */
    int keepAliveTimeSecond() default 60;

    /**
     * 队列bean
     * @return
     */
    String queueBeanName() default "";

}
