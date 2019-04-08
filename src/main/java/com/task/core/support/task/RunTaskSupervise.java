package com.task.core.support.task;


import com.task.core.support.task.support.RunTaskInfo;
import com.task.core.util.Audit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;


public final class RunTaskSupervise {

    private static final String EXCEPTION_NOT_FOUND = "The task doesn't exist";
    private static final String EXCEPTION_RUNNING = "The task is running";
    private static final String EXCEPTION_NOT_RUNNING = "The task is not running";
    private static final String EXCEPTION_TASK_OPERATION = "The task has been ";
    private static final String EXCEPTION_TASK_UNREASONABLE_NUMBER_THREADS = "Unreasonable number of threads";
    private static final String EXCEPTION_TASK_EXPECTATIONS_NUMBER_THREADS = "Number of threads exceeded expectations";

    private static final int MAX_THREAD_NUMBER = 1000;

    private static Logger logger = LogManager.getLogger(RunTaskSupervise.class);

    /**
     * 任务启用
     * @param taskId
     * @throws IllegalAccessException
     */
    public static synchronized void enableThread(String taskId) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunTaskInfo(taskId);
        //重复启动
        if(runTaskInfo.isEnable()) throw new IllegalAccessException(EXCEPTION_TASK_OPERATION + "enabled");
        logger.info("enable -- taskId:" + taskId );
        runTaskInfo.setEnable(true);
    }

    /**
     * 任务禁用
     * @param taskId
     * @throws IllegalAccessException
     */
    public static synchronized void disableThread(String taskId) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunTaskInfo(taskId);
        //重复禁用
        if(!runTaskInfo.isEnable()) throw new IllegalAccessException(EXCEPTION_TASK_OPERATION + "disable");
        //正在运行
        if(runTaskInfo.isRunTime()) throw new IllegalAccessException(EXCEPTION_RUNNING);
        logger.info("disable -- taskId:" + taskId );
        runTaskInfo.setEnable(false);
    }

    /**
     * 任务运行锁
     * @param taskId
     * @return
     * @throws IllegalAccessException
     */
    public static synchronized boolean runTheLock(String taskId)throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunTaskInfo(taskId);
        //正在运行
        if(runTaskInfo.isRunTime()) throw new IllegalAccessException(EXCEPTION_RUNNING);
        runTaskInfo.setRunTime(true);
        logger.info(runTaskInfo.getSgtin() + " task run ...");
        return true;
    }

    /**
     * 释放锁
     * @param taskId
     * @return
     * @throws IllegalAccessException
     */
    public static synchronized boolean releaseTheLock(String taskId)throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunTaskInfo(taskId);
        //正在运行
        if(!runTaskInfo.isRunTime())throw new IllegalAccessException(EXCEPTION_NOT_RUNNING);
        runTaskInfo.setRunTime(false);
        logger.info(runTaskInfo.getSgtin() + " task close");
        return true;
    }

    /**
     * 是否是运行状态
     * @param taskId
     * @return
     * @throws IllegalAccessException
     */
    public static synchronized boolean isItRunning(String taskId)throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunTaskInfo(taskId);
        return runTaskInfo.isRunTime();
    }

    /**
     * 赋予任务线程数
     * @param taskId
     * @param threadNumber
     * @return 历史value
     * @throws IllegalAccessException
     */
    public static synchronized int setRunThreadNumber(String taskId, Integer  threadNumber)throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunTaskInfo(taskId);
        //不符合规范的value
        if(threadNumber == null || threadNumber < 1) throw new IllegalAccessException(EXCEPTION_TASK_UNREASONABLE_NUMBER_THREADS);
        //value 过大
        if(threadNumber > MAX_THREAD_NUMBER) throw new IllegalAccessException(EXCEPTION_TASK_EXPECTATIONS_NUMBER_THREADS);
        int historyThreadNumber = runTaskInfo.getThreadNumber();
        runTaskInfo.setThreadNumber(threadNumber);
        //TODO
//        logger.info(HttpUtils.getUrl() + " threadNumber [ " + historyThreadNumber + " => " + threadNumber + " ]");
        return historyThreadNumber;
    }

    /**
     * 获得任务信息 (原始对象)
     * @param taskId
     * @return
     * @throws IllegalAccessException
     */
    static RunTaskInfo getRunTaskInfo(String taskId) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = TaskManageCoreSupervise.TASK_ALL.get(taskId);
        Audit.isNotNull("The task doesn't exist", runTaskInfo);
        return runTaskInfo;
    }

    /**
     * 追加运行日志
     * @param taskId
     * @param msg
     * @throws IllegalAccessException
     */
    public static void addLogger(String taskId, String msg) throws IllegalAccessException {
        RunTaskInfo runTaskInfo = getRunTaskInfo(taskId);
        runTaskInfo.getRunLogger().add(new RunTaskInfo.TaskRunLogger(LocalDateTime.now(), msg));
    }

}
