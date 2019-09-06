package com.task.core.start.component;

import com.task.core.bean.TaskManageConfigBean;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.task.group.ExcSupervise;
import com.task.core.support.task.group.TaskSuperviseAnalytical;
import com.task.core.support.thread.ThreadManageCore;
import com.task.core.support.web.StartController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.task.core.start.StartStatus;

/**
 * taskManageCore 启动器 Bean 注册
 * @author Frank
 */
@ComponentScan(basePackages = "com.task.core.*")
public class TaskBeanLoading {

    private Logger logger = LogManager.getLogger(TaskBeanLoading.class);

    @Bean
    public ProjectRunLogger projectRunLogger(){
        return new ProjectRunLogger();
    }


    @Bean
    public ExcSupervise excSupervise(@Autowired ThreadManageCore threadManageCore){
        ExcSupervise excSupervise = new ExcSupervise(threadManageCore);
        return excSupervise;
    }

    @Bean
    public TaskSuperviseAnalytical taskSuperviseAnalytical(@Autowired ExcSupervise excSupervise){

        TaskSuperviseAnalytical taskSuperviseAnalytical = new TaskSuperviseAnalytical(excSupervise);
        return taskSuperviseAnalytical;
    }

    /**
     * MVC 控制总线
     * @return
     */
    @Bean
    public StartController startController(){
        return new StartController();
    }


    @Bean
    public StartStatus startStatus(){
        return new StartStatus();
    }

}
