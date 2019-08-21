package com.run.controller;

import com.task.core.annotation.Task;
import com.task.core.enums.LogLevel;
import com.task.core.support.web.StartController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frank
 */
@RestController
@Task(
        code = "myTest",
        value = "测试任务",
        threadNumber = 10,
        level = LogLevel.DEBUG
)
@RequestMapping(value = "/qq")
public class TestController extends StartController {

    private Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    private ServiceTest serviceTest;

    @RequestMapping("/w")
    public String home(){
        return serviceTest.task();
    }


}
