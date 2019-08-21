package com.task.core.bean;

import com.task.core.enums.DataType;
import com.task.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskManageConfigBean {

    private ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

    private List<TaskEntityBean> taskEntityBeanList = new ArrayList<>();

    private List<String> packages = new ArrayList<>();

    //TODO 未完成线程池配置

    public TaskManageConfigBean setThreadNamePrefix(String threadNamePrefix){
        this.threadPoolConfig.setThreadNamePrefix(threadNamePrefix);
        return this;
    }

    public TaskManageConfigBean setMaxPoolSize(Integer maxPoolSize){
        this.threadPoolConfig.setMaxPool(maxPoolSize);
        return this;
    }

    public TaskManageConfigBean setThreadPriority(Integer threadPriorit){
        this.threadPoolConfig.setPriority(threadPriorit);
        return this;
    }

    public TaskManageConfigBean setCorePoolSize(Integer corePoolSize){
        this.threadPoolConfig.setCorePoolSize(corePoolSize);
        return this;
    }

    public TaskManageConfigBean setQueueCapacity(Integer queueCapacity){
        this.threadPoolConfig.setQueueCapacity(queueCapacity);
        return this;
    }


    public TaskManageConfigBean addScannerPackage(String packages){
        if(StringUtils.isBank(packages)) throw new NullPointerException("package is null");
        this.packages.add(packages);
        return this;
    }

    public TaskManageConfigBean addTask(String sgtin, String name, String describe, Integer threadNumber, Boolean enable,
                                        DataType dataType, String dataExp){
        TaskEntityBean taskEntityBean = new TaskEntityBean(sgtin, name, describe, threadNumber, enable, dataType, dataExp);
        taskEntityBeanList.add(taskEntityBean);
        return this;
    }

    public TaskManageConfigBean addTask(String sgtin, String name, String describe, Integer threadNumber, Boolean enable){
        addTask(sgtin, name, describe, threadNumber, enable, DataType.NOT_DATA, null);
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
