package com.task.core.bean;

import com.task.core.enums.DataType;
import com.task.core.enums.LogLevel;
import com.task.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置信息实体
 *
 * @author Frank
 */
public class TaskManageConfigBean {

    private ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

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
        if(StringUtils.isBlank(packages)) throw new NullPointerException("package is empty.");
        this.packages.add(packages);
        return this;
    }

    public ThreadPoolConfig getThreadPoolConfig() {
        return threadPoolConfig;
    }

    public List<String> getPackages() {
        return packages;
    }
}
