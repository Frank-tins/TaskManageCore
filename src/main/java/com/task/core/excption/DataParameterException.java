package com.task.core.excption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取数据参数获取异常
 *
 * @author Frank
 */
public class DataParameterException extends Exception{

    private Logger logger = LoggerFactory.getLogger(DataParameterException.class);

    public DataParameterException(String msg) {
        super(msg);
    }

}
