package com.run.controller;

import com.task.core.annotation.CreateTask;
import com.task.core.annotation.TaskService;
import com.task.core.enums.LogLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author Frank
 */
@Service
@TaskService
public class ServiceTest {

    private Logger logger = LogManager.getLogger(ServiceTest.class);

    int index = 0;

    @CreateTask(
            code = "myTest",
            value = "测试任务",
            threadNumber = 10,
            level = LogLevel.DEBUG
    )
    public String task(){
        return "你成了" + (index++);
    }

}
