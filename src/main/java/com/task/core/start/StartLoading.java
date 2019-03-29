package com.task.core.start;

import com.google.gson.Gson;
import com.task.core.bean.TaskManageConfigBean;
import com.task.core.excption.StartProblemException;
import com.task.core.support.task.RegisterLoading;
import com.task.core.util.Audit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 任务加载流
 * @author Frank
 */
@Component("taskConfigLading")
public class StartLoading implements CommandLineRunner {

    private Logger logger  = LogManager.getLogger(StartLoading.class);

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired(required = false)
    private TaskManageConfigBean taskManageConfigBean;


    @Override
    public void run(String... args) throws Exception {

        logger.error("taskConfigLading -- Init Task Manage Core FAILED .");
        registerLoading();
        destructor();

    }

    /**
     * 注册任务组
     * @throws StartProblemException 任务组注册出现问题时抛出
     */
    private void registerLoading() throws StartProblemException {
        RegisterLoading registerLoading = new RegisterLoading(taskManageConfigBean);
        try {
            registerLoading.run();
        } catch (Exception e) {
            logger.error(e);
            throw new StartProblemException(" Register Loading ");
        }
    }

    private void dependentLoading(){



    }

    /**
     * 销毁
     */
    private void destructor(){
        String [] names = defaultListableBeanFactory.getBeanNamesForType(this.getClass());
        Audit.arrayNotNull("taskConfigLading -- destructor failed  ", names);
        defaultListableBeanFactory.removeBeanDefinition("taskConfigLading");
        logger.info("taskConfigLading -- Init Task Manage Core SUCCESS");
    }





}
