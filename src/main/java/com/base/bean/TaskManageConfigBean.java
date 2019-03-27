package com.base.bean;

import com.base.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManageConfigBean {

    private ThreadPoolConfig threadPoolConfig;

    private List<TaskEntityBean> taskEntityBeanList = new ArrayList<>();

    private List<String> packages = new ArrayList<>();

    //TODO 未完成线程池配置

    public TaskManageConfigBean setThreadNamePrefix(String threadNamePrefix){
        return this;
    }

    public TaskManageConfigBean setMaxPoolSize(Integer maxPoolSize){
        return this;
    }

    public TaskManageConfigBean setThreadPriority(Integer threadPriorit){
        return this;
    }

    public TaskManageConfigBean setCorePoolSize(Integer threadPriorit){
        return this;
    }

    public TaskManageConfigBean setQueueCapacity(Integer threadPriorit){
        return this;
    }


    public TaskManageConfigBean addScannerPackage(String packages){
        if(StringUtils.isBank(packages)) throw new NullPointerException("package is null");
        this.packages.add(packages);
        return this;
    }

    public TaskManageConfigBean addTask(String sgtin, String name, String describe, Integer threadNumber, Boolean enable){
        TaskEntityBean taskEntityBean = new TaskEntityBean(sgtin, name, describe, threadNumber, enable);
        taskEntityBeanList.add(taskEntityBean);
        return this;
    }

    public TaskManageConfigBean addTask(String sgtin, String name, String describe){
        addTask(sgtin, name, describe, null, null);
        return this;
    }

    public TaskManageConfigBean addTask(Class type, String describe){
        addTask(type.getName() , null, describe, null, null);
        return this;
    }

    public TaskManageConfigBean addTask(String name, String describe){
        addTask(null, name, describe, null, null);
        return this;
    }

    public TaskManageConfigBean addTask(String name){
        addTask(null, name, null, null, null);
        return this;
    }

    public TaskManageConfigBean addTask(Class type){
        addTask(type.getName(), null, null, null, null);
        return this;
    }

    public TaskManageConfigBean addTaskEntity(TaskEntityBean taskEntityBean){
        if(taskEntityBean == null) throw new NullPointerException("TaskEntityBean is null");
        taskEntityBeanList.add(taskEntityBean);
        return this;
    }

    public ThreadPoolConfig getThreadPoolConfig() {
        return threadPoolConfig;
    }

    public List<TaskEntityBean> getTaskEntityBeanList() {
        return taskEntityBeanList;
    }

    public List<String> getPackages() {
        return packages;
    }
}
