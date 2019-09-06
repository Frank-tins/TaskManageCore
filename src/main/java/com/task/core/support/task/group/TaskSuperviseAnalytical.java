package com.task.core.support.task.group;

import com.task.core.annotation.CreateTask;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.task.RunTaskSupervise;
import com.task.core.bean.RunTaskInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 任务切面
 * @author Frank
 */
@Aspect
public class TaskSuperviseAnalytical {

    private Logger logger = LogManager.getLogger(TaskSuperviseAnalytical.class);

    /**
     * 执行器
     */
    private ExcSupervise excSupervise;

    public TaskSuperviseAnalytical(ExcSupervise excSupervise) {
        this.excSupervise = excSupervise;
        logger.debug("TMC [TaskSuperviseAnalytical(AOP)] [START]");
    }

    /**
     * 切入注解
     */
    @Pointcut("(@annotation(com.task.core.annotation.CreateTask))")
    private void cut() { }

    /**
     * 切面方法
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("cut()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable{
        //获得方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] parameters = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        String code = method.getDeclaredAnnotation(CreateTask.class).code();

        try {
            RunTaskSupervise.runTheLock(code);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error("任务无法锁定终止执行");
            return null;
        }

        try {
            RunTaskInfo runTaskInfo = ProjectRunLogger.getRunThread(code);
            String runId = TaskSupervise.registerTask(runTaskInfo.getThreadNumber(), runTaskInfo.getSgtin());
            Actuator actuator = new Actuator(method, target, parameters);
            excSupervise.startTask(actuator, runTaskInfo, runId);
            if(method.getReturnType() == String.class) {
                return runId;
            }
        } catch (Exception e){
            RunTaskSupervise.releaseTheLock(code);
            throw e;
        }
        return null;
    }

}
