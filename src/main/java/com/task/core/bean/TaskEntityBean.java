package com.task.core.bean;

import com.task.core.enums.DataType;

public class TaskEntityBean {

    private String sgtin;
    private String name;
    private String describe;
    private Integer threadNumber;
    private Boolean enable;
    private DataType dataType;
    private String dataExp;

    public TaskEntityBean(String sgtin, String name, String describe, Integer threadNumber,
                          Boolean enable, DataType dataType, String dataExp) {
        this.sgtin = sgtin;
        this.name = name;
        this.describe = describe;
        this.threadNumber = threadNumber;
        this.enable = enable;
    }

    public String getSgtin() {
        return sgtin;
    }

    public void setSgtin(String sgtin) {
        this.sgtin = sgtin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(Integer threadNumber) {
        this.threadNumber = threadNumber;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDataExp() {
        return dataExp;
    }

    public void setDataExp(String dataExp) {
        this.dataExp = dataExp;
    }
}
