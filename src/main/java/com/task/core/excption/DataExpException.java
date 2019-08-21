package com.task.core.excption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 数据表达式异常
 *
 * @author Frank
 */
public class DataExpException extends Exception{

    private Logger logger = LogManager.getLogger(DataExpException.class);

    public DataExpException(String msg) {
        super(msg);
    }

}
