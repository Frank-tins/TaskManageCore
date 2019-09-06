package com.task.core.support.task;

import com.task.core.enums.DataType;
import com.task.core.enums.LogLevel;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.bean.RunTaskInfo;
import com.task.core.util.GsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Frank
 */
public final class TaskManageCoreSupervise {

    private static Logger logger = LogManager.getLogger(TaskManageCoreSupervise.class);

    final static Map<String, RunTaskInfo> TASK_ALL = new HashMap<>();

    private static final ProjectRunLogger runLogger = new ProjectRunLogger();

    static {runLogger.init(TASK_ALL);}

    public static String register(String sgtin, String name, String describe, Integer threadNumber,
                                  Boolean enable, DataType dataType, String exp, LogLevel level, Method method){
        RunTaskInfo runTaskInfo = new RunTaskInfo(sgtin, name, describe, enable, threadNumber, dataType, exp, level, method);
        logger.info("TMC [TASK] register : " + sgtin + " Json: " + GsonUtils.toString(runTaskInfo));
        String errorMsg = "The same task sequence number cannot be registered.";
        if(TASK_ALL.get(sgtin) != null) {
            throw new RuntimeException(errorMsg);
        }
        TASK_ALL.put(sgtin, runTaskInfo);
        return sgtin;
    }


}
