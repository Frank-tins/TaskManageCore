package com.task.core.excption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 找不到目标对象
 *
 * @author Frank
 */
public class TargetNotFoundException extends RuntimeException {

    private Logger logger = LogManager.getLogger(TargetNotFoundException.class);

}
