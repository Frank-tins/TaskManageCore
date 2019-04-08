package com.task.core.bean;

public class ThreadPoolConfig {

    private Integer maxPool;

    private Integer prefix;

    private Integer priority;

    private Integer corePoolSize;

    private Integer queueCapacity;

    private String threadNamePrefix;

    private Integer keepAliveTimeSecond;


    public Integer getMaxPool() {
        return maxPool;
    }

    public Integer getPrefix() {
        return prefix;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public Integer getKeepAliveTimeSecond() {
        return keepAliveTimeSecond;
    }

    public void setMaxPool(Integer maxPool) {
        this.maxPool = maxPool;
    }

    public void setPrefix(Integer prefix) {
        this.prefix = prefix;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public void setKeepAliveTimeSecond(Integer keepAliveTimeSecond) {
        this.keepAliveTimeSecond = keepAliveTimeSecond;
    }
}
