package com.task.core.support.thread;

import com.task.core.bean.ThreadPoolConfig;
import com.task.core.support.thread.base.RunnableExtend;
import com.task.core.util.Audit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程处理器
 *
 * 线程池管理 &
 *
 * @author Frank
 */
class ThreadHandler {

    private Logger logger = LogManager.getLogger(ThreadHandler.class);

    static TaskThreadPool taskThreadPool ;

    static void init(ThreadPoolConfig threadPoolConfig){
        Audit.isNotNull("ERROR : Is an empty configuration. Failed to initialize thread set.", threadPoolConfig);
        taskThreadPool = new TaskThreadPool(threadPoolConfig);
    }

    public static void createThread(RunnableExtend runnableExtend){
        Audit.isNotNull("ERROR : Is an empty Runnable.", runnableExtend);
        ThreadPoolExecutor threadPoolExecutor = taskThreadPool.getThreadPoolExecutor();
        threadPoolExecutor.execute(runnableExtend);
    }


}
