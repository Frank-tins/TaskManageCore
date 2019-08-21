package com.run.controller;

import com.task.core.annotation.CreateTaskForce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author Frank
 */
@Service
public class ServiceTest {

    private Logger logger = LogManager.getLogger(ServiceTest.class);

    int index = 0;

    @CreateTaskForce(code = "myTest")
    public String task(){
        return "你成了" + (index++);
    }

}
