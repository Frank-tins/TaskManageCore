package com.task.core.support.thread;

import com.task.core.bean.RunTaskInfo;
import com.task.core.bean.ThreadPoolConfig;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.thread.base.RunnableExtend;
import com.task.core.support.thread.data.TaskRunnableLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Frank
 */
@Component
public class ThreadSportCore {

    private Logger logger = LogManager.getLogger(ThreadSportCore.class);

    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    private static TaskRunnableLocal taskRunnableLocal;

    public void setTaskRunnableLocal(TaskRunnableLocal taskRunnableLocal){
        if(this.taskRunnableLocal == null) this.taskRunnableLocal = taskRunnableLocal;
    }


    public void init(){
        ThreadHandler.init(threadPoolConfig);
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
