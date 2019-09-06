package com.task.core.excption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 获取数据参数获取异常
 *
 * @author Frank
 */
public class DataParameterException extends Exception{

    private Logger logger = LogManager.getLogger(DataParameterException.class);

    public DataParameterException(String msg) {
        super(msg);
    }

}
