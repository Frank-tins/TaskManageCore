package com.task.core.support.thread;

import com.task.core.bean.ThreadPoolConfig;
import com.task.core.util.Audit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程处理器
 *
 * @author Frank
 */
public class ThreadHandler {

    private Logger logger = LogManager.getLogger(ThreadHandler.class);

    private static TaskThreadPool taskThreadPool ;

    public static void init(ThreadPoolConfig threadPoolConfig){
        Audit.isNotNull("ERROR : Is an empty configuration. Failed to initialize thread set.", threadPoolConfig);
        taskThreadPool = new TaskThreadPool(threadPoolConfig);
    }

    public static void createThread(Runnable runnable){
        Audit.isNotNull("ERROR : Is an empty Runnable.", runnable);
        ThreadPoolExecutor threadPoolExecutor = taskThreadPool.getThreadPoolExecutor();
        threadPoolExecutor.execute(runnable);
    }


}
