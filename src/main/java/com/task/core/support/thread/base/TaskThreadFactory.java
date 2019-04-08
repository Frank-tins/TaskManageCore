package com.task.core.support.thread.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂
 *
 * @author Frank
 */
public class TaskThreadFactory implements ThreadFactory {

    private Logger logger = LogManager.getLogger(TaskThreadFactory.class);

    private final String threadName;

    private final Integer priority;

    private final boolean daemon;

    public TaskThreadFactory(String threadName, Integer priority) {
        this.threadName = threadName;
        this.priority = priority;
        this.daemon = true;
    }
    public TaskThreadFactory(String threadName, Integer priority,boolean daemon) {
        this.threadName = threadName;
        this.priority = priority;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread  = new Thread(r);
        thread.setName(threadName);
        thread.setPriority(priority);
        thread.setDaemon(daemon);
        return thread;
    }
}