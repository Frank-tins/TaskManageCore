package com.run.controller;

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

@RequestMapping(value = "/qq")
public class TestController {

    private Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    private ServiceTest serviceTest;

    @RequestMapping("/w")
    public String home(){
        return serviceTest.task();
    }


}
