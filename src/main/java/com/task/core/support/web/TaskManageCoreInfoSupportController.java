package com.task.core.support.web;

import com.task.core.bean.RunLogger;
import com.task.core.util.GsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class TaskManageCoreInfoSupportController implements Controller {

    private Logger logger = LogManager.getLogger(TaskManageCoreInfoSupportController.class);

//    @Autowired
    //TODO 需要装配日志
    private RunLogger runLogger;

    public TaskManageCoreInfoSupportController() {
        logger.info("TaskManageCoreInfoSupportController -- Initialize the information component");
    }

    @ResponseBody
    @PostMapping(value = "/info")
    public RunLogger getInfo() {
        System.out.println(GsonUtils.toString(runLogger));
        return runLogger;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView();
    }
}
