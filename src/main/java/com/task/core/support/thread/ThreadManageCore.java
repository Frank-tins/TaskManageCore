package com.task.core.support.thread;

import com.task.core.bean.RunTaskInfo;
import com.task.core.bean.ThreadPoolConfig;
import com.task.core.support.thread.base.RunnableExtend;
import com.task.core.support.thread.data.TaskRunnableLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 线程队列创建
 * 线程池初始化
 * 线程缓存操作
 * @author Frank
 */
public class ThreadManageCore {

    private Logger logger = LoggerFactory.getLogger(ThreadManageCore.class);

    public ThreadManageCore(ThreadPoolConfig threadPoolConfig) {
        this.threadPoolConfig = threadPoolConfig;
        logger.debug("TMC [THREAD MANAGE CORE] [START] ");
    }

    private ThreadPoolConfig threadPoolConfig;

    private static TaskRunnableLocal taskRunnableLocal;

    public void setTaskRunnableLocal(TaskRunnableLocal taskRunnableLocal){
        if (this.taskRunnableLocal == null) this.taskRunnableLocal = taskRunnableLocal;
    }


    public ThreadManageCore init(){
        ThreadHandler.init(threadPoolConfig);
        return this;
    }

    public void startTask(List<RunnableExtend> list){

        for (int i = 0; i < list.size(); i++) {
            ThreadHandler.createThread(list.get(i));
        }

    }

    public List<RunnableExtend> overTask(RunTaskInfo runTaskInfo){

        return taskRunnableLocal.cleanTaskCache(runTaskInfo);

    }

}
