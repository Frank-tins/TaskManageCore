package com.task.tset.run.controller;

import com.task.core.annotation.CreateTask;
import com.task.core.annotation.TaskService;
import com.task.core.enums.DataType;
import com.task.core.enums.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Frank
 */
@Service
@TaskService
public class ServiceTest {

    private Logger logger = LoggerFactory.getLogger(ServiceTest.class);

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
