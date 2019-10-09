package com.task.core.bean;

import com.task.core.util.StringUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.util.concurrent.BlockingQueue;

/**
 * 线程池配置信息实体
 *
 * @author Frank
 */
public class ThreadPoolConfig {

    private Integer maxPool;

    private Integer priority;

    private Integer corePoolSize;

    private Integer queueCapacity;

    private String threadNamePrefix;

    private Integer keepAliveTimeSecond;

    private BlockingQueue blockingQueue;

    public ThreadPoolConfig(){}

    public ThreadPoolConfig(AnnotationAttributes annotationAttributesArray) {
        this.maxPool = annotationAttributesArray.getNumber("maxPool").intValue();

        this.priority = annotationAttributesArray.getNumber("priority").intValue();

        this.corePoolSize = annotationAttributesArray.getNumber("corePoolSize").intValue();

        this.queueCapacity = annotationAttributesArray.getNumber("queueCapacity").intValue();

        this.keepAliveTimeSecond = annotationAttributesArray.getNumber("keepAliveTimeSecond").intValue();

        this.threadNamePrefix = annotationAttributesArray.getString("threadNamePrefix");

    }

    public BlockingQueue getBlockingQueue() {
        return blockingQueue;
    }

    public void setBlockingQueue(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public Integer getMaxPool() {
        return maxPool;
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

    public boolean isEnpty(){
        return maxPool == null
                && priority == null
                && corePoolSize == null
                && queueCapacity == null
                && keepAliveTimeSecond == null
                && StringUtils.isEmpty(threadNamePrefix)
                && blockingQueue == null;
    }
}
