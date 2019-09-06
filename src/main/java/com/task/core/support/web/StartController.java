package com.task.core.support.web;

import com.task.core.bean.RunTaskInfo;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.task.RunTaskSupervise;
import com.task.core.support.task.group.ExcSupervise;
import com.task.core.util.Audit;
import com.task.core.util.HttpUtils;
import com.task.core.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author Frank·tins
 */
@RequestMapping(value = "/tmc")
public final class StartController implements Controller {

    private Logger logger = LogManager.getLogger(StartController.class);

    private static final String threadNumberMsg = "threadNumber [history : %0] === >>> [new : %1]";
    @Autowired
    private ExcSupervise excSupervise;

    public StartController(){
        logger.debug("TMC [Web Manage Core] [START]");
    }

    @PostMapping("{code}/enable")
    public final TaskManageResponse enable(@PathVariable String code) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = auditCode(code);
        RunTaskSupervise.enableThread(code);
        return TaskManageResponse.SUCCESSDATA(runTaskInfo);
    }

    @PostMapping("{code}/disable")
    public final TaskManageResponse disable(@PathVariable String code) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = auditCode(code);
        RunTaskSupervise.disableThread(code);
        return TaskManageResponse.SUCCESSDATA(runTaskInfo);
    }

    @PostMapping("{code}/run")
    public final TaskManageResponse run(@PathVariable String code, Boolean isAsync) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = auditCode(code);
        if(!RunTaskSupervise.isItRunning(code)) {

            Map<String,Object> parameter = HttpUtils.getParameter("code", "isAsync");
            if(isAsync){
                excSupervise.asyncExc(runTaskInfo,parameter);
                return TaskManageResponse.SUCCESS("尝试唤醒数据任务", runTaskInfo);
            } else {
                try {
                    excSupervise.exc(runTaskInfo,parameter);
                    return TaskManageResponse.SUCCESS("唤醒成功等待执行任务组", runTaskInfo);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return TaskManageResponse.SUCCESS("唤醒失败", runTaskInfo);
                }
            }


        }else {
            return TaskManageResponse.ERROR("当前数据任务正在运行中", runTaskInfo);
        }
    }

    @RequestMapping(value = "{code}/info", method = {RequestMethod.GET, RequestMethod.POST})
    public final TaskManageResponse info(@PathVariable String code) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = auditCode(code);
        return TaskManageResponse.SUCCESS("SUCCESS", runTaskInfo);
    }

    @RequestMapping(value = "{code}/logger", method = {RequestMethod.GET, RequestMethod.POST})
    public final RunTaskInfo logger(@PathVariable String code) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = auditCode(code);
        return ProjectRunLogger.getRunThreadLogger(runTaskInfo.getSgtin());
    }

    @PostMapping(value = "{code}/power")
    public TaskManageResponse power(@PathVariable String code, Integer threadNumber) throws IllegalAccessException{
        RunTaskInfo runTaskInfo = auditCode(code);
        int historyThreadNumber = RunTaskSupervise.setRunThreadNumber(runTaskInfo.getSgtin(), threadNumber);
        String msg = StringUtils.format(threadNumberMsg, historyThreadNumber, threadNumber);
        return TaskManageResponse.SUCCESS(msg);
    }


    private RunTaskInfo auditCode(String code) throws IllegalAccessException {
        RunTaskInfo runTaskInfo = ProjectRunLogger.getRunThread(code);
        Audit.isNotBank("", code);
        Audit.isNotNull("", runTaskInfo);
        return runTaskInfo;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
}
