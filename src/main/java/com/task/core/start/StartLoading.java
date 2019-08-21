package com.task.core.start;

import com.task.core.excption.TMCStartProblemException;
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
@Component(value = StartLoading.START_BEAN_NAME)
public class StartLoading implements CommandLineRunner {

    public static final String START_BEAN_NAME = "taskConfigLading";

    private Logger logger  = LogManager.getLogger(StartLoading.class);

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    private RegisterLoading registerLoading;

    @Override
    public void run(String... args) throws Exception {


        registerLoading();
        try {
            destructor();
        } catch (TMCStartProblemException e) {
            logger.error(START_BEAN_NAME + " -- Init Task Manage Core FAILED .");
            e.printStackTrace();
        }

    }

    /**
     * 注册任务组
     * @throws TMCStartProblemException 任务组注册出现问题时抛出
     */
    private void registerLoading() throws TMCStartProblemException {
        try {
            registerLoading.run();
        } catch (Exception e) {
            logger.error(e);
            throw new TMCStartProblemException(" Register Loading ");
        }
    }

    private void dependentLoading(){ }

    /**
     * 销毁
     */
    private void destructor() throws TMCStartProblemException {
        String [] names = defaultListableBeanFactory.getBeanNamesForType(StartLoading.class);
        try {
            Audit.arrayNotNull("taskConfigLading -- destructor failed  ", names);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new TMCStartProblemException("Failed to close initialization object");
        }
        defaultListableBeanFactory.removeBeanDefinition(START_BEAN_NAME);
        logger.info("taskConfigLading -- Init Task Manage Core SUCCESS");
    }





}
