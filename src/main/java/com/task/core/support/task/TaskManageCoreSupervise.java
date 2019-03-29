package com.task.core.support.task;

import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.logger.support.RunLogger;
import com.task.core.support.task.support.RunTaskInfo;
import com.task.core.util.GsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 *
 */
public final class TaskManageCoreSupervise {

    private static Logger logger = LogManager.getLogger(TaskManageCoreSupervise.class);

    final static Map<String, RunTaskInfo> threadAll = new HashMap<>();

    private static final ProjectRunLogger runLogger = new ProjectRunLogger();

    static {runLogger.init(threadAll);}

    static String register(String sgtin, String name, String describe, Integer threadNumber, Boolean enable){
        RunTaskInfo runTaskInfo = new RunTaskInfo(sgtin, name, describe, enable, threadNumber);
        logger.info("register : " + sgtin + " Json: " + GsonUtils.toString(runTaskInfo));
        if(threadAll.get(sgtin) != null) throw new Error("The same task sequence number cannot be registered.");
        threadAll.put(sgtin, runTaskInfo);
        return sgtin;
    }

    public static Set<RunTaskInfo> getEnableThread(){
        Set<RunTaskInfo> enables = new HashSet<>();
        threadAll.values().forEach(runThreadInfo -> {
            if(runThreadInfo.isEnable()) enables.add(runThreadInfo);
        });
        return enables;
    }

    public static Set<RunTaskInfo> getAll(){
        return new HashSet<>(threadAll.values());
    }

    static RunLogger getRunLogger(){
        return runLogger.getRunLogger(getEnableThread());
    }

}
