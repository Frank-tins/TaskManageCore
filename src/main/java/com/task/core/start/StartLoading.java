package com.task.core.start;

import com.task.core.config.TaskCoreConfiguration;
import com.task.core.excption.TMCStartProblemException;
import com.task.core.util.Audit;
import com.task.core.util.GsonUtils;
import com.task.core.util.SpringUtil;
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
public class StartLoading extends TaskCoreConfiguration {

    private Logger logger  = LogManager.getLogger(StartLoading.class);

    @Override
    public void run(String... args) throws Exception {

        registerLoading();
//        for (String  beanDefinitionName: SpringUtil.getApplicationContext().getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }


    }

    /**
     * 注册任务组
     * @throws TMCStartProblemException 任务组注册出现问题时抛出
     */
    private void registerLoading() throws TMCStartProblemException {
        try {
            new RegisterLoading(taskManageConfigBean).run();
        } catch (Exception e) {
            logger.error(e);
            throw new TMCStartProblemException(" Register Loading ");
        }
    }

}
