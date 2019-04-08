package com.task.core.excption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 加载流引线异常
 *
 * @author Frank
 */
public class TMCStartProblemException extends Exception{

    private Logger logger = LogManager.getLogger(TMCStartProblemException.class);

    public TMCStartProblemException(String message) {
        super(message);
        logger.debug("Load Fail " + message);
    }
}
