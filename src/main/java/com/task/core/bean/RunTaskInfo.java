package com.task.core.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.core.enums.DataType;
import com.task.core.enums.LogLevel;
import com.task.core.util.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务注册信息
 *
 * @author Frank
 */
public class RunTaskInfo implements Serializable{

    private String sgtin;

    private String name;

    private String describe;

    private boolean enable;

    private int threadNumber;

    private boolean runTime;

    private DataType dataType;

    private String exp;

    private List<TaskRunLogger> runLogger;

    private LogLevel level;

    @JsonIgnore
    private transient Method method;

    public RunTaskInfo() {};

    public RunTaskInfo(String sgtin, String name, String describe, boolean enable, int threadNumber, DataType dataType, String exp, LogLevel level, Method method) {
        this.sgtin = sgtin;
        this.name = name;
        this.describe = describe;
        this.enable = enable;
        this.threadNumber = threadNumber;
        this.runTime = false;
        this.runLogger = new ArrayList<>();
        this.dataType = dataType;
        this.exp = exp;
        this.level = level;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
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
        this.runLogger = new ArrayList<>();
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
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
        (StringUtils.isNotBlank(runTaskInfo.sgtin)
                && StringUtils.isNotBlank(this.sgtin)
                && runTaskInfo.sgtin.equals(this.sgtin));
    }

    @Override
    public RunTaskInfo clone() {
        RunTaskInfo runTaskInfo = new RunTaskInfo();
        BeanUtils.copyProperties(this, runTaskInfo);
        return runTaskInfo;
    }



}