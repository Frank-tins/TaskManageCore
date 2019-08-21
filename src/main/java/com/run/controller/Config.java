package com.run.controller;

import com.task.core.bean.TaskManageConfigBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Frank
 */
@Configuration
public class Config {

    private Logger logger = LogManager.getLogger(Config.class);

    @Bean
    public TaskManageConfigBean taskManageConfigBean(){
        TaskManageConfigBean taskManageConfigBean = new TaskManageConfigBean();
        taskManageConfigBean.addScannerPackage("com.run.controller");
        return taskManageConfigBean;
    }

}
