package com.task.core.excption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 找不到目标对象
 *
 * @author Frank
 */
public class TargetNotFoundException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(TargetNotFoundException.class);

}
