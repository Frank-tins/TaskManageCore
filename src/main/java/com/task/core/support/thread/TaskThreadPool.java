package com.task.core.support.thread;

import com.task.core.bean.ThreadPoolConfig;
import com.task.core.support.thread.base.TaskRejectedExecutionHandler;
import com.task.core.support.thread.base.TaskThreadFactory;
import com.task.core.util.Filling;
import com.task.core.util.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务线程池监管
 *  Create Thread pool
 * @author Frank
 */
class TaskThreadPool {

    private Logger logger = LoggerFactory.getLogger(TaskThreadPool.class);

    private final ThreadPoolExecutor threadPoolExecutor ;

    TaskThreadPool(ThreadPoolConfig threadPoolConfig) {

        threadPoolConfig = defaultParameter(threadPoolConfig);

        //线程工厂初始化
        String threadNamePrefix = threadPoolConfig.getThreadNamePrefix();
        Integer priority = threadPoolConfig.getPriority();
        TaskThreadFactory taskThreadFactory = new TaskThreadFactory(threadNamePrefix, priority);

        //异常任务处理器初始化
        TaskRejectedExecutionHandler taskRejectedExecutionHandler = new TaskRejectedExecutionHandler();

        //允许外部队列创建
        BlockingQueue<Runnable> queue = threadPoolConfig.getBlockingQueue();
        //当用户没有配置线程池队列大小时取无穷大
        if(queue == null) {
            //队列初始化
            int queueSize = threadPoolConfig.getQueueCapacity();
            queue = queueSize > 0 ? new LinkedBlockingQueue<Runnable>(queueSize) : new LinkedBlockingQueue<Runnable>();
        }
        //线程池初始化
        int corePoolSize = threadPoolConfig.getCorePoolSize();
        int maxPool = threadPoolConfig.getMaxPool();
        int keepAliveTimeSecond = threadPoolConfig.getKeepAliveTimeSecond();
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPool, keepAliveTimeSecond, TimeUnit.SECONDS, queue,
                taskThreadFactory, taskRejectedExecutionHandler);

        logger.info("TMS [THREAD POOL INIT] : " + GsonUtils.toString(threadPoolConfig));

    }

    private ThreadPoolConfig defaultParameter(ThreadPoolConfig threadPoolConfig){
        ThreadPoolConfig config = new ThreadPoolConfig();
        config.setQueueCapacity(-1);
        config.setCorePoolSize(50);
        config.setPriority(5);
        config.setMaxPool(Integer.MAX_VALUE);
        config.setThreadNamePrefix("task-manage-core");
        config.setKeepAliveTimeSecond(60);
        return Filling.autoFilling(ThreadPoolConfig.class, threadPoolConfig, config);
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }


}
