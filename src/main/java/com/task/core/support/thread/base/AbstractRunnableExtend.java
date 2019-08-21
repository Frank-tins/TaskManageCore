package com.task.core.support.thread.base;

import com.task.core.bean.RunTaskInfo;
import com.task.core.enums.RunStatus;
import com.task.core.support.task.group.TaskSupervise;
import com.task.core.support.task.group.TaskSuperviseAnalytical;
import com.task.core.support.thread.data.RunnableCache;
import com.task.core.support.thread.data.TaskRunnableLocal;
import com.task.core.util.GsonUtils;
import com.task.core.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Frank
 */
public abstract class AbstractRunnableExtend implements RunnableExtend<RunStatus> {

    private Logger logger = LogManager.getLogger(AbstractRunnableExtend.class);

    private TaskRunnableLocal taskRunnableLocal = TaskRunnableLocal.getTaskRunnableLocal();

    private RunnableCache<RunTaskInfo> runnableCache = null;

    private Object functionExecuteResult = null;

    private RunStatus executeResult = null;

    private boolean isError = false;

    protected TaskRunnableLocal getTaskRunnableLocal() {
        return taskRunnableLocal;
    }

    protected RunnableCache getRunnableCache() {
        return runnableCache;
    }

    private static final String EXECUTE_DATA = "executeData";

    private static final String EXECUTE_RESULT = "executeResult";

    private static final String ERROR_LOG = "taskObject [name : %0  ] [code : %1] execute error.";

    private static final String ERROR_DATA_LOG = "execute data : %0";

    private static final String ERROR_MSG = "ERROR_MSG";

    @Override
    public final void run() {
        prepare();
        Throwable throwableObj = null;
        try {
            functionExecuteResult = execute();

        } catch (Throwable throwable) {
            throwableObj = throwable(throwable);
        }
        clean();
        if(throwableObj != null){
            error(throwableObj);
        }else {
            over();
        }
    }


    public Throwable throwable(Throwable throwable) {

        RunTaskInfo runTaskInfo = runnableCache.getMountTheValue();
        String errorLog = StringUtils.format(ERROR_LOG, runTaskInfo.getName(), runTaskInfo.getSgtin());
        logger.error(errorLog);

        Object data = runnableCache.getCache(RunnableExtend.PARAMETER_DATA);
        String dataLog = StringUtils.format(ERROR_DATA_LOG, GsonUtils.toString(data));
        logger.error(dataLog);

        logger.error(throwable);
        isError = true;
        runnableCache.addCache(ERROR_MSG, throwable.getMessage());
        return throwable;

    }

    @Override
    public void prepare() {
        this.runnableCache = taskRunnableLocal.cache(this);
    }

    @Override
    public void putRunResultsTheCache(Map allCache) {
        RunStatus executeResult = null;
        Object data = allCache.get(RunnableExtend.PARAMETER_DATA);
        if(isError){
            executeResult = RunStatus.MALFUNCTION(data, allCache.get(ERROR_MSG).toString());
        }else{
            executeResult = RunStatus.ACCOMPLISH(data,  functionExecuteResult);
        }
        this.executeResult = executeResult;
    }

    @Override
    public RunStatus getRunResultsTheCache() {
        return executeResult;
    }

}
