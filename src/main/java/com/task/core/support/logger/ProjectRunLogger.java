package com.task.core.support.logger;

import com.task.core.support.logger.support.RunLogger;
import com.task.core.support.task.support.RunTaskInfo;
import com.task.core.util.Audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 运行信息管理
 * @author Todd
 */
public class ProjectRunLogger {

//    private final RunLogger runLogger = new RunLogger(HttpUtils.getIpAddress(), HttpUtils.getLocalPort());
    private final RunLogger runLogger = new RunLogger("", "");
    //所有任务
    private static Map<String, RunTaskInfo> threadAll = null;

    public void init(Map<String, RunTaskInfo> threadAll){
        this.threadAll = threadAll;
    }

    /**
     * 获得任务信息
     * @param treadId
     * @return
     * @throws IllegalAccessException
     */
    public static RunTaskInfo getRunThread(String treadId)throws IllegalAccessException{
        RunTaskInfo runTaskInfo = getRunThreadLogger(treadId);
        runTaskInfo.removeLogger();
        return runTaskInfo;
    }

    /**
     * 获得任务所有信息 （克隆对象）
     * @param treadId
     * @return
     * @throws IllegalAccessException
     */
    public static RunTaskInfo getRunThreadLogger(String treadId)throws IllegalAccessException{
        return getRunThreadInfo(treadId).clone();
    }

    /**
     * 获得全局信息
     * @param enableRunTaskInfos
     * @return
     */
    public RunLogger getRunLogger(Collection<RunTaskInfo> enableRunTaskInfos){
        List<String> enables = new ArrayList<>();
        enableRunTaskInfos.forEach(runThreadInfo -> enables.add(runThreadInfo.getSgtin()));
        runLogger.setEnableThread(new ArrayList<>(enables));
        runLogger.setRunTaskInfos(new ArrayList<>(threadAll.values()));
        return runLogger;
    }

    private static RunTaskInfo getRunThreadInfo(String treadId) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = threadAll.get(treadId);
        Audit.isNotNull("The task doesn't exist", runTaskInfo);
        return runTaskInfo;
    }
}
