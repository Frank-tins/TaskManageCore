package com.task.core.start;

import com.task.core.annotation.Task;
import com.task.core.bean.TaskEntityBean;
import com.task.core.bean.TaskManageConfigBean;
import com.task.core.support.task.TaskManageCoreSupervise;
import com.task.core.util.Audit;
import com.task.core.util.ClassScaner;
import com.task.core.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 任务注册器
 * @author Frank
 */
public class RegisterLoading  {

    private Logger logger  = LogManager.getLogger(RegisterLoading.class);

    private final String DEFAULT_PARAMETER = "[null]";

    private final boolean DEFAULT_RUN_STATUS = true;

    private final int DEFAULT_THREAD_NUMBER = 100;

    private TaskManageConfigBean taskManageConfigBean;

    public RegisterLoading(TaskManageConfigBean taskManageConfigBean) {
        this.taskManageConfigBean = taskManageConfigBean;
    }

    public void run() throws Exception {
        //加载许可校验
        Audit.isNotNull("The task pool is not listening for any valid tasks", taskManageConfigBean);
        //存在2.0 配置则 调用加载流
        if(taskManageConfigBean != null)  scannerLoading();
    }

    private void scannerLoading(){
        List<String> packages = taskManageConfigBean.getPackages();
        if( packages.size() > 0 ) {
            String [] temp = new String[]{};
            Set<Class> types = ClassScaner.scan(packages.toArray(temp), Task.class);
            types.forEach(type -> {
                Task task = (Task) type.getDeclaredAnnotation(Task.class);
                register(task.code() , task.value(), task.describe(), task.threadNumber(), task.isEnable());
            });
        }
        List<TaskEntityBean> taskEntityBeanList = taskManageConfigBean.getTaskEntityBeanList();
        if(taskEntityBeanList.size() > 0){
            taskEntityBeanList.forEach( taskEntity -> register(taskEntity.getSgtin(), taskEntity.getName(), taskEntity.getDescribe(), taskEntity.getThreadNumber(), taskEntity.getEnable()));
        }
    }

    private void register(String sgtin, String name, String describe, Integer threadNumber, Boolean enable){

        Audit.thereAreValidValues("Invalid task configuration ", sgtin, name, describe, threadNumber, enable);

        sgtin = StringUtils.isBank(sgtin) ? UUID.randomUUID().toString().replace("-","") : sgtin;
        name = StringUtils.isBank(name) ? DEFAULT_PARAMETER : name;
        describe = StringUtils.isBank(describe) ? DEFAULT_PARAMETER : describe;
        threadNumber = threadNumber == null || threadNumber < 1 ? DEFAULT_THREAD_NUMBER : threadNumber;
        enable = enable == null ?  DEFAULT_RUN_STATUS : enable;

        Audit.isNotNull("Invalid task configuration ", sgtin, name, describe, threadNumber, enable);

        TaskManageCoreSupervise.register(sgtin, name, describe, threadNumber, enable);
    }


}
