package com.task.core.excption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载流引线异常
 *
 * @author Frank
 */
public class TMCStartProblemException extends Exception{

    private Logger logger = LoggerFactory.getLogger(TMCStartProblemException.class);

    public TMCStartProblemException(String message) {
        super(message);
        logger.debug("Load Fail " + message);
    }
}
