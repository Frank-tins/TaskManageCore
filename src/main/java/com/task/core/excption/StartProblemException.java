package com.task.core.excption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 加载流引线异常
 *
 * @author Frank
 */
public class StartProblemException extends Exception{

    private Logger logger = LogManager.getLogger(StartProblemException.class);

    public StartProblemException(String message) {
        super(message);
        logger.debug("Load Fail " + message);
    }
}
