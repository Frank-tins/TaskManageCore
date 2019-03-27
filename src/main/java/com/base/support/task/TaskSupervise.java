package com.base.support.task;

import com.base.support.thread.RunTaskSupervise;
import com.base.enums.RunStatus;
import com.base.util.CollectionUtils;
import com.base.util.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class TaskSupervise {


    private static final Map<String, TaskInfo> TASK_TABLE = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(TaskSupervise.class);

    /**
     * 任务拆分
     * @param datas
     * @param threadNum
     * @return
     */
    public static final List<List> allocationTask(List datas, int threadNum){
        return CollectionUtils.averageAssign(datas, threadNum);
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
        String uuid = uuid = UUID.randomUUID().toString().replace("-", "");
        if(TASK_TABLE.get(runId) != null) logger.error("error runId.");
        if(StringUtils.isNotBank(runId)) uuid = runId;
        TaskInfo taskInfo = new TaskInfo(uuid, new ArrayList<>(), taskId, taskNumber);
        TASK_TABLE.put(uuid, taskInfo);
        return uuid;
    }


    /**
     * 分步告知完成
     * @param runID
     * @param runStatus
     */
    public final static void conclude(String runID, RunStatus runStatus){
        TaskInfo taskInfo = TASK_TABLE.get(runID);
        Integer taskNumber = taskInfo.getTaskThreadNumber();
        List<RunStatus> tasks = taskInfo.getRunCondition();
        if(taskNumber == null || tasks == null) return;
        tasks.add(runStatus);
        if(taskNumber == tasks.size()) taskOver(runID);
    }

    public final static void conclude(String runID){
        conclude(runID, RunStatus.ACCOMPLISH());
    }

    public final static void conclude(String runID,String msg){
        conclude(runID, StringUtils.isBank(msg) ?
                RunStatus.ACCOMPLISH() : RunStatus.MALFUNCTION(msg));
    }

    /**
     * 任务结束
     * @param runID
     */
    private final static void taskOver(String runID){
        TaskInfo taskInfo = TASK_TABLE.get(runID);
        generateLogger(runID);
        TASK_TABLE.remove(runID);
        try {
            RunTaskSupervise.releaseTheLock(taskInfo.getTaskId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成任务执行日志
     * @param runID
     */
    private final static void generateLogger (String runID){
        TaskInfo taskInfo = TASK_TABLE.get(runID);
        List<RunStatus> runs = taskInfo.getRunCondition();
        StringBuffer stringBuffer = new StringBuffer();
        runs.forEach(e -> {
            if(e.equals(RunStatus.Status.MALFUNCTION))
                stringBuffer.append(DateUtils.formatDate(new Date(e.getRunTime()), "yyyy-MM-dd hh:mm:ss")
                        + " -- " + e.getMessage() + "\n");
        });
        try {
            RunTaskSupervise.addLogger(taskInfo.getTaskId(), stringBuffer.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private static class TaskInfo{

        private String runId;

        private List<RunStatus> runCondition;

        private String taskId;

        private int TaskThreadNumber;

        public TaskInfo(String runId, List<RunStatus> runCondition, String taskId, int TaskThreadNumber) {
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
