package com.task.core.start;

import com.task.core.bean.TaskManageConfigBean;
import com.task.core.excption.TMCStartProblemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * 任务加载流
 * @author Frank
 */
public final class StartStatus implements CommandLineRunner {

    private static Status status = Status.SUCCESS;
    private Logger logger  = LoggerFactory.getLogger(StartStatus.class);

    public static void warning(){
        if(Status.SUCCESS == status ){
            status = Status.WARNING;
        }
    }

    public static void error(){
        if(Status.ERROR != status ){
            status = Status.ERROR;
        }
    }

    @Override
    public void run(String... args) throws Exception {

        if (Status.SUCCESS == status){
            logger.info("TMC task manage core startup was successful");
        }

        if (Status.WARNING == status){
            logger.info("TMC task manage core startup was successful, but there were some warnings at configuration time ");
        }

        if(Status.ERROR  == status){
            logger.info("TMC task manage core boot failure");
        }

    }

    private enum Status{

        SUCCESS,
        ERROR,
        WARNING

    }


}
