package com.task.core.support.task.group;


import com.task.core.annotation.CreateTaskForce;
import com.task.core.annotation.Task;
import com.task.core.support.data.DataSupportCore;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.task.RunTaskSupervise;
import com.task.core.support.thread.ThreadSportCore;
import com.task.core.bean.RunTaskInfo;
import com.task.core.support.thread.base.RunnableExtend;
import com.task.core.support.thread.base.RunnableExtendWork;
import com.task.core.support.thread.data.TaskRunnableLocal;
import com.task.core.util.ArrayUtils;
import com.task.core.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class TaskSuperviseAnalytical {

    private Logger logger = LogManager.getLogger(TaskSuperviseAnalytical.class);

    public static final String PARAMETER_RUN_ID = "RUN_ID";

    public static final String PARAMETER_PROCEEDING_JOIN_POINT = "PROCEEDING_JOIN_POINT";

    public static final String PARAMETER_DATA = "PARAMETER_DATA";

    private ThreadSportCore threadSportCore;

    private DataSupportCore dataSupportCore = new DataSupportCore();

    private TaskRunnableLocal taskRunnableLocal = TaskRunnableLocal.getTaskRunnableLocal();

    public TaskSuperviseAnalytical(@Autowired ThreadSportCore threadSportCore) {
        this.threadSportCore = threadSportCore;
        this.threadSportCore.setTaskRunnableLocal(taskRunnableLocal);
        logger.info("Task supervise analytical [start].");
    }

    @Pointcut("(@annotation(com.task.core.annotation.CreateTaskForce))")
    private void cut() { }

    @Around("cut()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable{
        //获得方法
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        Method method = msig.getMethod();
        Object target = joinPoint.getTarget();
        //检查支持
//        if(!(obj instanceof StartController))return new Error("Unsupported type");
//        StartController startController = (StartController)obj;
//        String taskId = startController.getThreadId();

        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        Object[] parameters = joinPoint.getArgs();

        String code ="";

        //获得注解
        CreateTaskForce createTask = method.getDeclaredAnnotation(CreateTaskForce.class);
//        //不执行监管
//        if(!createTask.value()) return joinPoint.proceed();
        if(StringUtils.isBank(createTask.code())) {
            Class targetType = target.getClass();
            Task task = (Task) targetType.getAnnotation(Task.class);
            code = task.code();
        } else {
            code = createTask.code();
        }
        try {
            RunTaskSupervise.runTheLock(code);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        RunTaskInfo runTaskInfo = ProjectRunLogger.getRunThread(code);

        String runId = TaskSupervise.registerTask(runTaskInfo.getThreadNumber(), runTaskInfo.getSgtin());//StringUtils.UUID() + runTaskInfo.getSgtin();

        List data = dataSupportCore.getData(runTaskInfo.getDataType(), runTaskInfo.getExp(), method, parameters);

        List<RunnableExtend> list = new ArrayList<>();
        TaskRunnableLocal.LocalRunnableBinding localRunnableBinding = taskRunnableLocal.getLocalRunnableBinding(runTaskInfo);
        //TODO 安全执行时间
//        if(){
        boolean parameterIsExist = data != null && data.size() > 0;
        Map<String, Object> cache = new HashMap<>();
        cache.put(PARAMETER_PROCEEDING_JOIN_POINT, joinPoint);
        cache.put(PARAMETER_RUN_ID, runId);
        List dataArray = TaskSupervise.allocationTask(data, runTaskInfo.getThreadNumber());
        for (int i = 0; i < runTaskInfo.getThreadNumber(); i++) {
            if(parameterIsExist) {
                int index = dataSupportCore.getDataParameterIndex(method);
                if(index <0 ) throw new RuntimeException("find parameter index error.");
                parameters[index] = dataArray.get(i);
                cache.put(PARAMETER_DATA, parameters);
            }
            localRunnableBinding.bind(new RunnableExtendWork(), cache);
        }
        localRunnableBinding.bindData();
        list = localRunnableBinding.bindData();
        threadSportCore.startTask(list);
        if(method.getReturnType() == String.class) return runId;

        return null;


//        //获得当前任务的配置信息
////        RunTaskInfo runTaskInfo = ProjectRunLogger.getRunThread(code);
//////        runTaskInf
////
////
////        String threadNumberName = "";
////        String runIdName = "";
////        //参数Array
//////        Parameter [] parameter = method.getParameters();
//////        //注解检索
//////        for (int i = 0; i < parameter.length; i++) {
//////            if(parameter[i].getAnnotation(RunId.class) != null)
//////                runIdName = parameter[i].getName();
//////            if(parameter[i].getAnnotation(ThreadNumber.class) != null)
//////                threadNumberName = parameter[i].getName();
//////        }
////
////        String taskId = "";
////        String runId = "";
////        //获得是否设置了RunId
////        for (int i = 0; i < parameters.length; i++) {
////            if (PARAMETER_RUN_ID.equals(parameterNames[i])
////                    || StringUtils.isNotBank(runIdName) && runIdName.equals(parameterNames[i]))
////                if(parameters[i] instanceof String
////                        && (StartController.AUTO_RUN_ID !=  ((String)parameters[i]) && StringUtils.isNotBank((parameters[i]))) )
////                    runId = (String) parameters[i];
////            if(PARAMETER_TASK_ID.equals(parameterNames[i])) {
////                taskId = (String) parameters[i];
////            }
////        }
////        if(StringUtils.isBank(taskId))return new Error("The thread Id could not be obtained");
////
////        int threadNumber = createTask.single() ? 1 : runTaskInfo.getThreadNumber();
////        if(!runTaskInfo.isEnable())  {
////            logger.error("task disabled");
////            return null;
////        }
////        //锁定任务
////        RunTaskSupervise.runTheLock(taskId);
////
////        //使用RunId 注册
////        runId = StringUtils.isBank(runId) ? TaskSupervise.registerTask(threadNumber, taskId)
////                : TaskSupervise.registerTask(threadNumber, taskId, runId);
////        //参数更新
////        for (int i = 0; i < parameters.length; i++) {
////            if (PARAMETER_RUN_ID.equals(parameterNames[i])
////                    || StringUtils.isNotBank(runIdName) && runIdName.equals(parameterNames[i])) parameters[i] = runId;
////            if (PARAMETER_THREAD_NUMBER.equals(parameterNames[i])
////                    || StringUtils.isNotBank(threadNumberName) && threadNumberName.equals(parameterNames[i]))
////                if(parameters[i] instanceof Integer &&
////                        (StartController.AUTO_THREAD_NUMBER ==  ((int)parameters[i]) || ((int)parameters[i]) < 1 ))
////                    parameters[i] = threadNumber;
////        }
////
////
////
////        new Thread(){
////            @Override
////            public void run() {
////                try {
////                    joinPoint.proceed(parameters);
////                } catch (Throwable throwable) {
////                    throw new RuntimeException(throwable);
////                }
////            }
////        }.start();

//        return runId;

    }


//    public List<Object> getData(){
//
//
//
//    }


    public static void overTask(){

    }

}
