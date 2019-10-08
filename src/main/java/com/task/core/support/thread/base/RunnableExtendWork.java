package com.task.core.support.thread.base;

import com.task.core.bean.RunStatus;
import com.task.core.support.task.group.Actuator;
import com.task.core.support.task.group.TaskSupervise;
import com.task.core.support.thread.data.RunnableCache;
import com.task.core.support.thread.data.TaskRunnableLocal;
import com.task.core.util.Audit;

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
        Actuator actuator = (Actuator) runnableCache.getCache(RunnableExtend.PARAMETER_PROCEEDING_METHOD);
        String runId = (String) runnableCache.getCache(RunnableExtend.PARAMETER_RUN_ID);
        //TODO 参数不支持启动
        Audit.isNotNull("runnable parameter ", actuator, runId);
    }

    @Override
    public Object execute() throws Throwable {
        RunnableCache runnableCache = super.getRunnableCache();
        Actuator actuator = (Actuator) runnableCache.getCache(RunnableExtend.PARAMETER_PROCEEDING_METHOD);
        Object para = runnableCache.getCache(RunnableExtend.PARAMETER_DATA);
        Object[] parameters = para == null ?  null : (Object[]) para;
        Object rel = null;
        if(parameters != null) {
            rel = actuator.invoke(parameters);
        }else {
            rel = actuator.invoke();
        }
        return rel;
    }

    @Override
    public void error(Throwable throwable) {

    }
}
