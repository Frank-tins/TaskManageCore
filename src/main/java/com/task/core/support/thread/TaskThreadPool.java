package com.task.core.support.thread;

import com.task.core.bean.ThreadPoolConfig;
import com.task.core.support.thread.base.TaskRejectedExecutionHandler;
import com.task.core.support.thread.base.TaskThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务线程池监管
 *
 * @author Frank
 */
class TaskThreadPool {

    private Logger logger = LogManager.getLogger(TaskThreadPool.class);

    private final ThreadPoolExecutor threadPoolExecutor ;

    TaskThreadPool(ThreadPoolConfig threadPoolConfig) {

        //线程工厂初始化
        String threadNamePrefix = threadPoolConfig.getThreadNamePrefix();
        Integer priority = threadPoolConfig.getPriority();
        TaskThreadFactory taskThreadFactory = new TaskThreadFactory(threadNamePrefix, priority);

        //异常任务处理器初始化
        TaskRejectedExecutionHandler taskRejectedExecutionHandler = new TaskRejectedExecutionHandler();

        //队列初始化
        int queueSize = threadPoolConfig.getQueueCapacity();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue(queueSize);

        //线程池初始化
        int corePoolSize = threadPoolConfig.getCorePoolSize();
        int maxPool = threadPoolConfig.getMaxPool();
        int keepAliveTimeSecond = threadPoolConfig.getKeepAliveTimeSecond();
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPool, keepAliveTimeSecond, TimeUnit.SECONDS, queue,
                taskThreadFactory, taskRejectedExecutionHandler);

    }

    private ThreadPoolConfig defaultParameter(ThreadPoolConfig threadPoolConfig){
        return threadPoolConfig;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
