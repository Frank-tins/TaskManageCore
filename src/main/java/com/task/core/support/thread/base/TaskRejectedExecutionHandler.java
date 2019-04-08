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
        //TODO 暂未想出拒绝策略方案
    }

}
