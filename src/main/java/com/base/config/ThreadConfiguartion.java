package com.base.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfiguartion {

    @Value(value = "${thread.pool.max-size}")
    private int threadPoolMaxSize;
    @Value(value = "${thread.pool.priority}")
    private int threadPriority;
    @Value(value = "${thread.core-pool-size}")
    private int threadCorePoolSize;
    @Value(value = "${thread.pool.queue-capacity}")
    private int threadQueueCapacity;

    private transient final int DEFAULT_PRIORITY = 5;
    private transient Logger logger = LogManager.getLogger(ThreadConfiguartion.class);

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        logger.info("Initialize Thread Pool..");
        if(threadPriority < 0 && threadPriority > 10){
            logger.error("错误的默认优先级配置 [已重置]");
            threadPriority = DEFAULT_PRIORITY;
        }
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async-oracle-clien");
        executor.setMaxPoolSize(threadPoolMaxSize);
        executor.setThreadPriority(threadPriority);
        executor.setCorePoolSize(threadCorePoolSize);
        executor.setQueueCapacity(threadQueueCapacity);
        executor.initialize();
        return executor;
    }

}
