package com.task.core.support.thread.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 拒绝处理策略
 *
 * @author Frank
 */
public class TaskRejectedExecutionHandler implements RejectedExecutionHandler {

    private Logger logger = LogManager.getLogger(TaskRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.info("任务拒绝!");
        throw new RuntimeException();
//        Thread.sleep();
        //TODO 暂未想出拒绝策略方案
        //初步方案是想使用 task id 与 run id 记录此次任务信息.
        // 设想 在主线程池溢出的情况下发出警告 并 创建一个备用线程池, 当备用线程池也无法容纳的时候提出错误
    }

}
