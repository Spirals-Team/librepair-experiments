package com.almundo.test.callcenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {
    @Value("${number.of.requests}")
    private int numberOfRequests;

    @Value("${number.of.threads}")
    private int numberOfThreads;

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(numberOfThreads);
        executor.setMaxPoolSize(numberOfThreads);
        executor.setQueueCapacity(numberOfRequests);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
        return executor;
    }
}