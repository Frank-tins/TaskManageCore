package com.task.core.support.thread.base;

import java.util.Map;

/**
 *
 * @param <R>
 * @author Frank
 */
public interface RunnableExtend<R> extends Runnable {

    String PARAMETER_RUN_ID = "RUN_ID";

    String PARAMETER_PROCEEDING_METHOD = "PARAMETER_PROCEEDING_METHOD";

    String PARAMETER_DATA = "PARAMETER_DATA";

    /**
     * 结束事件
     */
    void over();

    /**
     * 清除缓存事件
     */
    void clean();

    /**
     * 准备事件
     */
    void prepare();

    /**
     * 异常事件
     * @param throwable 产生的异常
     */
    void error(Throwable throwable);

    /**
     * 执行事件
     * @return
     * @throws Throwable
     */
    Object execute() throws Throwable;

    /**
     * 缓存装
     * @param allCache
     */
    void putRunResultsTheCache(Map allCache);

    /**
     * 缓存卸载
     * @return
     */
    R getRunResultsTheCache();
}
