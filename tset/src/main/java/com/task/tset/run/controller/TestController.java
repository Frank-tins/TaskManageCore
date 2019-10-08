package com.task.tset.run.controller;

import com.task.core.support.web.StartController;
import com.task.tset.run.bean.type.MethodDataTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frank
 */
@RestController

@RequestMapping(value = "/qq")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ServiceTest serviceTest;

    @Autowired
    private MethodDataTest methodDataTest;


    @RequestMapping("/start-test")
    public String home(){
        return serviceTest.task();
    }


    @RequestMapping("/betm-test")
    public String betm(){
        return methodDataTest.methodDataTestFunction( "Supper Man", null);
    }


}
