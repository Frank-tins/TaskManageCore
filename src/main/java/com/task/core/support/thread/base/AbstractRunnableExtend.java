package com.task.core.support.thread.base;

import com.task.core.bean.RunTaskInfo;
import com.task.core.bean.RunStatus;
import com.task.core.support.thread.data.RunnableCache;
import com.task.core.support.thread.data.TaskRunnableLocal;
import com.task.core.util.GsonUtils;
import com.task.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 任务挂载器抽象类
 * @author Frank
 */
public abstract class AbstractRunnableExtend implements RunnableExtend<RunStatus> {

    private Logger logger = LoggerFactory.getLogger(AbstractRunnableExtend.class);
    /**
     * 缓存控制器
     */
    private TaskRunnableLocal taskRunnableLocal = TaskRunnableLocal.getTaskRunnableLocal();

    /**
     * 挂载器所属缓存
     */
    private RunnableCache<RunTaskInfo> runnableCache = null;

    /**
     * 目标方法执行结果
     */
    private Object functionExecuteResult = null;

    /**
     * 系统规则结果
     */
    private RunStatus executeResult = null;

    /**
     * 是否存在执行异常
     */
    private boolean isError = false;

    /**
     * 向子类提供 挂载器
     * @return
     */
    protected TaskRunnableLocal getTaskRunnableLocal() {
        return taskRunnableLocal;
    }

    /**
     * 向子类提供 缓存
     * @return
     */
    protected RunnableCache getRunnableCache() {
        return runnableCache;
    }

    private static final String ERROR_LOG = "taskObject [name : %0  ] [code : %1] execute error.";

    private static final String ERROR_DATA_LOG = "execute data : %0";

    private static final String ERROR_MSG = "ERROR_MSG";

    /**
     * 执行方法
     */
    @Override
    public final void run() {

        //TODO 设想 - 异常处理
        // 现阶段问题
        //   : 无法控制准备阶段异常与 缓存阶段异常 以及 结束和 异常方法中产出的异常也会导致问题
        //   : 产出后果是 runnable 一旦产生异常无法回调异常通知器 则导致任务无法终止
        // 可优化点
        //   : 异常与 失败是否需要分开识别
        // 异常所指是全文中出现的异常
        // 失败则所指业务异常
        // 单方面从任务处理核心上来说不太需要区分
        // 但从任务挂载器角度讲 是可以进行区分的 不过不太适合以异常单位区分
        // 或许我可以从此处改进 对其进行返回值区分 比如让用户传入指定的返回值 我对其进行值匹配
        // 大体上可以更好的包装和分化线程操作
        // 实现的上的话 可能会有点小麻烦
        // 异常体系的话也必须因此改版
        // 将异常体系分化成 各个节点锁产出的异常点
        // 比如准备异常 执行异常 等 传入枚举进入@see com.task.core.support.thread.base.RunnableExtend.error(Throwable)
        // 改造为 @see com.task.core.support.thread.base.RunnableExtend.error(Throwable, enum)
        // 从异常角度来看似乎更为合理

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

    /**
     * 错误系统处理方法
     * @param throwable
     * @return
     */
    public Throwable throwable(Throwable throwable) {

        RunTaskInfo runTaskInfo = runnableCache.getMountTheValue();
        String errorLog = StringUtils.format(ERROR_LOG, runTaskInfo.getName(), runTaskInfo.getSgtin());
        logger.error(errorLog);

        Object data = runnableCache.getCache(RunnableExtend.PARAMETER_DATA);
        String dataLog = StringUtils.format(ERROR_DATA_LOG, GsonUtils.toString(data));
        logger.error(dataLog);

        logger.error("TMC thread execute error.", throwable);
        isError = true;
        runnableCache.addCache(ERROR_MSG, throwable.getMessage());
        return throwable;

    }

    /**
     * 默认准备方法
     */
    @Override
    public void prepare() {
        this.runnableCache = taskRunnableLocal.cache(this);
    }

    /**
     * 执行完成后的缓存填充
     * @param allCache
     */
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

    /**
     * 获取执行完成后缓存器中的缓存
     * @return
     */
    @Override
    public RunStatus getRunResultsTheCache() {
        return executeResult;
    }

}
