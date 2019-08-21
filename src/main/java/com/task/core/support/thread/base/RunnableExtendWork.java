package com.task.core.support.thread.base;

import com.task.core.support.task.group.TaskSupervise;
import com.task.core.support.task.group.TaskSuperviseAnalytical;
import com.task.core.support.thread.data.RunnableCache;
import com.task.core.support.thread.data.TaskRunnableLocal;
import com.task.core.util.Audit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * thread work
 *
 * @author Frank
 */
public class RunnableExtendWork implements RunnableExtend {

    private Logger logger = LogManager.getLogger(RunnableExtendWork.class);

    private TaskRunnableLocal taskRunnableLocal = TaskRunnableLocal.getTaskRunnableLocal();

    @Override
    public void clean() {

    }

    @Override
    public void over() {

    }

    @Override
    public void prepare() {
        RunnableCache runnableCache = taskRunnableLocal.cache(this);
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) runnableCache.getCache(TaskSuperviseAnalytical.PARAMETER_PROCEEDING_JOIN_POINT);
        String runId = (String) runnableCache.getCache(TaskSuperviseAnalytical.PARAMETER_RUN_ID);
        //TODO 参数不支持启动
        Audit.isNotNull("runnable parameter ", proceedingJoinPoint, runId);
    }

    @Override
    public void execute() {
        RunnableCache runnableCache = taskRunnableLocal.cache(this);
        String runId = (String) runnableCache.getCache(TaskSuperviseAnalytical.PARAMETER_RUN_ID);
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) runnableCache.getCache(TaskSuperviseAnalytical.PARAMETER_PROCEEDING_JOIN_POINT);
        Object[] parameters = (Object[]) runnableCache.getCache(TaskSuperviseAnalytical.PARAMETER_DATA);
        try {
            if(parameters != null) {
                proceedingJoinPoint.proceed(parameters);
            }else {
                proceedingJoinPoint.proceed();
            }
            TaskSupervise.conclude(runId);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            TaskSupervise.conclude(runId, throwable.getMessage());
        }

    }

    @Override
    public void putRunResultsTheCache(Map allCache) {

    }

    @Override
    public void getRunResultsTheCache() {

    }
}
