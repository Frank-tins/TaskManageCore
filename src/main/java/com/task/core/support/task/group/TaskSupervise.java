package com.task.core.support.task.group;

import com.task.core.bean.RunTaskInfo;
import com.task.core.enums.LogLevel;
import com.task.core.bean.RunStatus;
import com.task.core.enums.Status;
import com.task.core.support.logger.ProjectRunLogger;
import com.task.core.support.task.RunTaskSupervise;
import com.task.core.support.thread.base.RunnableExtend;
import com.task.core.support.thread.data.TaskRunnableLocal;
import com.task.core.util.CollectionUtils;
import com.task.core.util.GsonUtils;
import com.task.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 任务注册器
 * @author Frank
 */
public class TaskSupervise {


    private static final Map<String, RunEntityInfo> TASK_TABLE = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(TaskSupervise.class);



    /**
     * 任务拆分
     * @param dataArray
     * @param threadNum
     * @return
     */
    public static final List<List> allocationTask(List dataArray, int threadNum){
        return CollectionUtils.averageAssign(dataArray, threadNum);
    }

    /**
     * 注册任务
     * @param taskNumber
     * @return
     */
    static final String registerTask(int taskNumber, String taskId){
        return registerTask(taskNumber, taskId, null);
    }

    static final String registerTask(int taskNumber, String taskId, String runId){
        String uuid = uuid = StringUtils.UUID() + taskId;
        if(TASK_TABLE.get(runId) != null) logger.error("error runId.");
        if(StringUtils.isNotBlank(runId)) uuid = runId;
        RunEntityInfo runEntityInfo = new RunEntityInfo(uuid, new ArrayList<>(), taskId, taskNumber);
        TASK_TABLE.put(uuid, runEntityInfo);
        return uuid;
    }


    /**
     * 分步告知完成
     * @param runID
     * @param runnableExtend
     */
    public final static void conclude(String runID, RunnableExtend<RunStatus> runnableExtend){
        RunStatus runStatus = runnableExtend.getRunResultsTheCache();
        RunEntityInfo runEntityInfo = TASK_TABLE.get(runID);
        Integer taskNumber = runEntityInfo.getTaskThreadNumber();
        List<RunStatus> tasks = runEntityInfo.getRunCondition();

        if(taskNumber == null || tasks == null) return;
        tasks.add(runStatus);
        if(taskNumber == tasks.size()) {
            try {
                taskOver(runID);
            } catch (IllegalAccessException e) {
                logger.error("task over error.");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 任务结束
     * @param runID
     */
    private final static void taskOver(String runID) throws IllegalAccessException {
        RunEntityInfo runEntityInfo = TASK_TABLE.get(runID);
        TaskRunnableLocal taskRunnableLocal = ExcSupervise.getTaskRunnableLocal();
        RunTaskInfo runTaskInfo = null;
        try {
            runTaskInfo = ProjectRunLogger.getRunThread(runEntityInfo.getTaskId());
        } catch (IllegalAccessException e) {
            logger.error("get task info is null.");
            throw e;
        }
        generateLogger(runID, runTaskInfo);
        TASK_TABLE.remove(runID);
        try {
            RunTaskSupervise.releaseTheLock(runEntityInfo.getTaskId());
        } catch (IllegalAccessException e) {
            logger.error("close lock task error.");
            throw e;
        }
    }

    /**
     * 生成任务执行日志
     * @param runID
     */
    private final static void generateLogger (String runID, RunTaskInfo runTaskInfo){
        RunEntityInfo runEntityInfo = TASK_TABLE.get(runID);
        List<RunStatus> runs = runEntityInfo.getRunCondition();
        LogLevel logLevel = runTaskInfo.getLevel();
        StringBuffer stringBuffer = new StringBuffer();
         switch (logLevel){
             case ERROR_INFO:
                 runs.forEach(e -> {
                     if (e.equals(Status.MALFUNCTION)) stringBuffer.append(GsonUtils.toStringConvertDate(e));
                 });
                 break;
             case DEBUG:
                 runs.forEach(e -> {
                      stringBuffer.append(GsonUtils.toStringConvertDate(e));
                 });
                 break;
             default:
         };
        try {
            RunTaskSupervise.addLogger(runEntityInfo.getTaskId(), stringBuffer.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    private static class RunEntityInfo {

        private String runId;

        private List<RunStatus> runCondition;

        private String taskId;

        private int TaskThreadNumber;

        public RunEntityInfo(String runId, List<RunStatus> runCondition, String taskId, int TaskThreadNumber) {
            this.runId = runId;
            this.runCondition = runCondition;
            this.taskId = taskId;
            this.TaskThreadNumber = TaskThreadNumber;
        }

        public String getRunId() {
            return runId;
        }

        public List<RunStatus> getRunCondition() {
            return runCondition;
        }

        public String getTaskId() {
            return taskId;
        }

        public int getTaskThreadNumber() {
            return TaskThreadNumber;
        }


    }
}
