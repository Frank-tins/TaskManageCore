package com.task.core.support.task.support;

import com.task.core.util.StringUtils;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RunTaskInfo {

    private String sgtin;

    private String name;

    private String describe;

    private boolean enable;

    private int threadNumber;

    private boolean runTime;

    private List<TaskRunLogger> runLogger;

    public RunTaskInfo() {};

    public RunTaskInfo(String sgtin, String name, String describe, boolean enable, int threadNumber) {
        this.sgtin = sgtin;
        this.name = name;
        this.describe = describe;
        this.enable = enable;
        this.threadNumber = threadNumber;
        this.runTime = false;
        this.runLogger = new ArrayList<>();
    }

    public void setRunTime(boolean runTime) {
        this.runTime = runTime;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public String getSgtin() {
        return sgtin;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public boolean isEnable() {
        return enable;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public boolean isRunTime() {
        return runTime;
    }

    public List<TaskRunLogger> getRunLogger() {
        return runLogger;
    }

    public void setSgtin(String sgtin) {
        this.sgtin = sgtin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setRunLogger(List<TaskRunLogger> runLogger) {
        this.runLogger = runLogger;
    }

    public void removeLogger(){
        this.runLogger = null;
    }

    @Override
    public int hashCode() {
        return sgtin.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof RunTaskInfo))
            return false;
        RunTaskInfo runTaskInfo = (RunTaskInfo) obj;
        return (runTaskInfo.sgtin == this.sgtin) ||
        (StringUtils.isNotBank(runTaskInfo.sgtin)
                && StringUtils.isNotBank(this.sgtin)
                && runTaskInfo.sgtin.equals(this.sgtin));
    }

    @Override
    public RunTaskInfo clone() {
        RunTaskInfo runTaskInfo = new RunTaskInfo();
        BeanUtils.copyProperties(this, runTaskInfo);
        return runTaskInfo;
    }

    public static class TaskRunLogger{

        private LocalDateTime runTime;
        private String msg;

        public TaskRunLogger(LocalDateTime runTime, String msg) {
            this.runTime = runTime;
            this.msg = msg;
        }

    }

}