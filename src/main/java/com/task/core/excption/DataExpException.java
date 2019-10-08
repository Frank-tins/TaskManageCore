package com.task.core.excption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据表达式异常
 *
 * @author Frank
 */
public class DataExpException extends Exception{

    private Logger logger = LoggerFactory.getLogger(DataExpException.class);

    public DataExpException(String msg) {
        super(msg);
    }

}
