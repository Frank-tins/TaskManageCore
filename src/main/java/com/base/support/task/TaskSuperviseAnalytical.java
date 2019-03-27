package com.base.support.task;


import com.base.annotation.CreateTaskForce;
import com.base.annotation.RunId;
import com.base.annotation.ThreadNumber;
import com.base.support.logger.ProjectRunLogger;
import com.base.support.thread.RunTaskSupervise;
import com.base.support.thread.support.RunTaskInfo;
import com.base.support.StartController;
import com.base.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class TaskSuperviseAnalytical {

    @Pointcut("(@annotation(com.base.annotation.CreateTaskForce))")
    private void cut() { }

    private Logger logger = LogManager.getLogger(TaskSuperviseAnalytical.class);

    private static final String PARAMETER_RUN_ID = "runId";

    private static final String PARAMETER_THREAD_NUMBER = "ThreadNumber";

    private static final String PARAMETER_TASK_ID = "taskId";

    @Around("cut()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable{
        //获得方法
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        Method method = msig.getMethod();
        Object obj1 = joinPoint.getTarget();
        Object obj = joinPoint.getThis();
        //检查支持
//        if(!(obj instanceof StartController))return new Error("Unsupported type");
//        StartController startController = (StartController)obj;
//        String taskId = startController.getThreadId();

        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        Object[] parameters = joinPoint.getArgs();
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);



        //获得注解
        CreateTaskForce createTask = method.getDeclaredAnnotation(CreateTaskForce.class);
        if(!createTask.value()) return joinPoint.proceed();



        String threadNumberName = "";
        String runIdName = "";
        //参数Array
        Parameter [] parameter = method.getParameters();
        //注解检索
        for (int i = 0; i < parameter.length; i++) {
            if(parameter[i].getAnnotation(RunId.class) != null)
                runIdName = parameter[i].getName();
            if(parameter[i].getAnnotation(ThreadNumber.class) != null)
                threadNumberName = parameter[i].getName();
        }

        String taskId = "";
        String runId = "";
        //获得是否设置了RunId
        for (int i = 0; i < parameters.length; i++) {
            if (PARAMETER_RUN_ID.equals(parameterNames[i])
                    || StringUtils.isNotBank(runIdName) && runIdName.equals(parameterNames[i]))
                if(parameters[i] instanceof String
                        && (StartController.AUTO_RUN_ID !=  ((String)parameters[i]) && StringUtils.isNotBank((parameters[i]))) )
                    runId = (String) parameters[i];
            if(PARAMETER_TASK_ID.equals(parameterNames[i])) {
                taskId = (String) parameters[i];
            }
        }
        if(StringUtils.isBank(taskId))return new Error("The thread Id could not be obtained");
        //获得当前任务的配置信息
        RunTaskInfo runTaskInfo = ProjectRunLogger.getRunThread(taskId);
        int threadNumber = createTask.single() ? 1 : runTaskInfo.getThreadNumber();
        if(!runTaskInfo.isEnable())  {
            logger.error("task disabled");
            return null;
        }
        //锁定任务
        RunTaskSupervise.runTheLock(taskId);

        //使用RunId 注册
        runId = StringUtils.isBank(runId) ? TaskSupervise.registerTask(threadNumber, taskId)
                : TaskSupervise.registerTask(threadNumber, taskId, runId);
        //参数更新
        for (int i = 0; i < parameters.length; i++) {
            if (PARAMETER_RUN_ID.equals(parameterNames[i])
                    || StringUtils.isNotBank(runIdName) && runIdName.equals(parameterNames[i])) parameters[i] = runId;
            if (PARAMETER_THREAD_NUMBER.equals(parameterNames[i])
                    || StringUtils.isNotBank(threadNumberName) && threadNumberName.equals(parameterNames[i]))
                if(parameters[i] instanceof Integer &&
                        (StartController.AUTO_THREAD_NUMBER ==  ((int)parameters[i]) || ((int)parameters[i]) < 1 ))
                    parameters[i] = threadNumber;
        }



        new Thread(){
            @Override
            public void run() {
                try {
                    joinPoint.proceed(parameters);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            }
        }.start();

        return runId;
    }

}
