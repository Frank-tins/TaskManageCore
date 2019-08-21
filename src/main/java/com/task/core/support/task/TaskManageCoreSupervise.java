package com.task.core.support.task;

import com.task.core.enums.DataType;
import com.task.core.enums.LogLevel;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.bean.RunTaskInfo;
import com.task.core.util.GsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 *
 */
public final class TaskManageCoreSupervise {

    private static Logger logger = LogManager.getLogger(TaskManageCoreSupervise.class);

    final static Map<String, RunTaskInfo> TASK_ALL = new HashMap<>();

    private static final ProjectRunLogger runLogger = new ProjectRunLogger();

    static {runLogger.init(TASK_ALL);}

    public static String register(String sgtin, String name, String describe, Integer threadNumber, Boolean enable, DataType dataType, String exp, LogLevel level){
        RunTaskInfo runTaskInfo = new RunTaskInfo(sgtin, name, describe, enable, threadNumber, dataType, exp, level);
        logger.info("register : " + sgtin + " Json: " + GsonUtils.toString(runTaskInfo));
        if(TASK_ALL.get(sgtin) != null) throw new Error("The same task sequence number cannot be registered.");
        TASK_ALL.put(sgtin, runTaskInfo);
        return sgtin;
    }

    public static Set<RunTaskInfo> getEnableThread(){
        Set<RunTaskInfo> enables = new HashSet<>();
        TASK_ALL.values().forEach(runThreadInfo -> {
            if(runThreadInfo.isEnable()) enables.add(runThreadInfo);
        });
        return enables;
    }

//    public static Set<RunTaskInfo> getAll(){
//        return new HashSet<>(threadAll.values());
//    }
//
//    static RunLogger getRunLogger(){
//        return runLogger.getRunLogger(getEnableThread());
//    }

}
