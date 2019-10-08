package com.task.core.support.task.group;

import com.task.core.bean.RunTaskInfo;
import com.task.core.support.data.DataSupportCore;
import com.task.core.support.task.RunTaskSupervise;
import com.task.core.support.thread.ThreadManageCore;
import com.task.core.support.thread.base.RunnableExtend;
import com.task.core.support.thread.base.RunnableExtendWork;
import com.task.core.support.thread.data.TaskRunnableLocal;
import com.task.core.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 执行器
 *
 * @author Frank
 */
public class ExcSupervise {

    /**
     * 缓存器
     */
    private static TaskRunnableLocal taskRunnableLocal = TaskRunnableLocal.getTaskRunnableLocal();
    private Logger logger = LoggerFactory.getLogger(ExcSupervise.class);
    /**
     * 线程处理核心
     */
    private ThreadManageCore threadManageCore;
    /**
     * 数据处理核心
     */
    private DataSupportCore dataSupportCore = new DataSupportCore();

    public ExcSupervise(ThreadManageCore threadManageCore) {
        this.threadManageCore = threadManageCore;
        this.threadManageCore.setTaskRunnableLocal(taskRunnableLocal);
        logger.info("TMC [Execute Manage] [START].");
    }

    /**
     * 获取缓存器
     * @return
     */
    static TaskRunnableLocal getTaskRunnableLocal(){
        return taskRunnableLocal;
    }

    /**
     * 异步执行任务
     * @param runTaskInfo
     * @param argsMap
     * @return
     */
    public Thread asyncExc(RunTaskInfo runTaskInfo, Map<String, Object> argsMap){

        Thread thread =  new Thread(){
            @Override
            public void run() {
                try {
                    exc(runTaskInfo, argsMap);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        };
        thread.start();
        return thread;

    }

    /**
     * 执行任务
     * @param runTaskInfo
     * @param argsMap
     * @throws Throwable
     */
    public void exc(RunTaskInfo runTaskInfo, Map<String, Object> argsMap) throws Throwable{
        String runId = null;
        String code = runTaskInfo.getSgtin();

        Method method = runTaskInfo.getMethod();
        Object [] args = TypeUtils.packageMethodParameter(method, argsMap);
        RunTaskSupervise.runTheLock(code);
        runId = TaskSupervise.registerTask(runTaskInfo.getThreadNumber(), runTaskInfo.getSgtin());
        Actuator actuator = new Actuator(method, null, args);

        try {
//            startTask(actuator, runTaskInfo, runId);
        } catch (Exception e) {
            try {
                RunTaskSupervise.releaseTheLock(code);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            throw e;
        }


    }

    /**
     * 启动任务线程
     * @param actuator
     * @param runTaskInfo
     * @param runId
     * @throws Exception
     */
    void startTask(Actuator actuator, RunTaskInfo runTaskInfo, String runId) throws Exception{

        if (!runTaskInfo.isEnable()){}

        Object [] args = actuator.getArgs();

        List data = dataSupportCore.getData(runTaskInfo.getDataType(), runTaskInfo.getExp(), runTaskInfo.getMethod(), args);

        List<RunnableExtend> list = new ArrayList<>();
        TaskRunnableLocal.LocalRunnableBinding localRunnableBinding = taskRunnableLocal.getLocalRunnableBinding(runTaskInfo);
        boolean parameterIsExist = data != null && data.size() > 0;
        List dataArray = null;
        if(parameterIsExist) {
            dataArray = TaskSupervise.allocationTask(data, runTaskInfo.getThreadNumber());
        }
        for (int i = 0; i < runTaskInfo.getThreadNumber(); i++) {
            Object [] argsActuator = Arrays.copyOf(args, args.length);
            Map<String, Object> cache = new HashMap<>();

            cache.put(RunnableExtend.PARAMETER_RUN_ID, runId);
            if(parameterIsExist) {
                int index = dataSupportCore.getDataParameterIndex(actuator.getMethod());
                if(index <0 ){ throw new RuntimeException("find parameter index error.");}
                argsActuator[index] = dataArray.get(i);
                /*actuator.createExt(argsActuator) argsActuator*/
                cache.put(RunnableExtend.PARAMETER_PROCEEDING_METHOD, actuator);
                cache.put(RunnableExtend.PARAMETER_DATA, argsActuator);
            }else {
                cache.put(RunnableExtend.PARAMETER_PROCEEDING_METHOD, actuator);
            }
            localRunnableBinding.bind(new RunnableExtendWork(), cache);
        }
        localRunnableBinding.bindData();
        list = localRunnableBinding.bindData();
        threadManageCore.startTask(list);

    }


}
