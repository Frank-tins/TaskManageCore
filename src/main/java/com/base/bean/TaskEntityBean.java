package com.base.bean;

public class TaskEntityBean {

    private String sgtin;
    private String name;
    private String describe;
    private Integer threadNumber;
    private Boolean enable;

    public TaskEntityBean(String sgtin, String name, String describe, Integer threadNumber, Boolean enable) {
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
}
