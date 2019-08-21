package com.task.core.config;

import com.task.core.bean.TaskManageConfigBean;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.thread.ThreadSportCore;
import com.task.core.support.web.TaskManageCoreInfoSupportController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Frank
 */
@ComponentScan(basePackages = "com.task.core.*")
public abstract class TaskCoreConfiguration implements CommandLineRunner {

    private Logger logger = LogManager.getLogger(TaskCoreConfiguration.class);
    @Autowired(required = false)
    protected TaskManageConfigBean taskManageConfigBean;

    @Bean
    public ProjectRunLogger projectRunLogger(){
        return new ProjectRunLogger();
    }

    /**
     * 基于MVC
     * @return
     */
    @Bean
    public TaskManageCoreInfoSupportController taskManageCoreInfoSupportController(){

        return new TaskManageCoreInfoSupportController();
    }

    @Bean
    public ThreadSportCore threadSportCore(){
        if(taskManageConfigBean == null) this.taskManageConfigBean = new TaskManageConfigBean();
        return new ThreadSportCore(taskManageConfigBean.getThreadPoolConfig()).init();
    }


}
