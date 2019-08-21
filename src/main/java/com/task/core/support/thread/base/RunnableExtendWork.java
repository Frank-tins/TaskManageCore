package com.task.core.support.thread.base;

import com.task.core.enums.RunStatus;
import com.task.core.support.task.group.TaskSupervise;
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
public class RunnableExtendWork extends AbstractRunnableExtend implements RunnableExtend<RunStatus> {

    @Override
    public void clean() {
        TaskRunnableLocal taskRunnableLocal = super.getTaskRunnableLocal();
        taskRunnableLocal.cleanRunnableCache(this);
        RunnableCache runnableCache = super.getRunnableCache();
        String runId = (String) runnableCache.getCache(RunnableExtend.PARAMETER_RUN_ID);
        TaskSupervise.conclude(runId, this);
    }

    @Override
    public void over() {

    }

    @Override
    public void prepare() {
        super.prepare();
        RunnableCache runnableCache = super.getRunnableCache();
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) runnableCache.getCache(RunnableExtend.PARAMETER_PROCEEDING_JOIN_POINT);
        String runId = (String) runnableCache.getCache(RunnableExtend.PARAMETER_RUN_ID);
        //TODO 参数不支持启动
        Audit.isNotNull("runnable parameter ", proceedingJoinPoint, runId);
    }

    @Override
    public Object execute() throws Throwable {
        RunnableCache runnableCache = super.getRunnableCache();
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) runnableCache.getCache(RunnableExtend.PARAMETER_PROCEEDING_JOIN_POINT);
        Object[] parameters = (Object[]) runnableCache.getCache(RunnableExtend.PARAMETER_DATA);
        Object rel = null;
        if(parameters != null) {
            rel = proceedingJoinPoint.proceed(parameters);
        }else {
            rel = proceedingJoinPoint.proceed();
        }
        return rel;
    }

    @Override
    public void error(Throwable throwable) {

    }
}
