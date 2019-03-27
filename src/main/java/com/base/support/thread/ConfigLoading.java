package com.base.support.thread;

import com.base.annotation.Task;
import com.base.bean.TaskEntityBean;
import com.base.bean.TaskManageConfigBean;
import com.base.util.Audit;
import com.base.util.ClassScaner;
import com.base.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 任务模型加载流
 * @author Frank
 */
@Component("taskConfigLading")
public class ConfigLoading implements CommandLineRunner {

    private Logger logger  = LogManager.getLogger(ConfigLoading.class);

    private static final String DEFAULT_PARAMETER = "[null]";

    private static final boolean DEFAULT_RUN_STATUS = true;

    public static final int DEFAULT_THREAD_NUMBER = 100;

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired(required = false)
    private TaskManageConfigBean taskManageConfigBean;


    @Override
    public void run(String... args) throws Exception {
        //加载许可校验
        Audit.objectAndArrayIsNotNull("The task pool is not listening for any valid tasks", taskManageConfigBean, TaskSupervise.threadAll);
        //存在2.0 配置则 调用加载流
        if(taskManageConfigBean != null)  sacnnerLoading ();
        //销毁
        destructor();
    }

    private void sacnnerLoading (){
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

        TaskSupervise.register(sgtin, name, describe, threadNumber, enable);
    }


    private void destructor(){
        defaultListableBeanFactory.removeBeanDefinition("taskConfigLading");
        logger.info("Init Task Manage Core SUCCESS");
    }
}
