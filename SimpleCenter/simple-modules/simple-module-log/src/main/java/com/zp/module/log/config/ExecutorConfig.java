package com.zp.module.log.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {

    @Value("${log.sysLog.corePoolSize:5}")
    private int sysCorePoolSize;
    @Value("${log.sysLog.maxPoolSize:20}")
    private int sysMaxPoolSize;
    @Value("${log.sysLog.maxQueue:100}")
    private int sysMaxQueue;
    @Value("${log.sysLog.keepAlive:120}")
    private int sysKeepAlive;

    @Value("${log.loginLog.corePoolSize:5}")
    private int loginCorePoolSize;
    @Value("${log.loginLog.maxPoolSize:20}")
    private int loginMaxPoolSize;
    @Value("${log.loginLog.maxQueue:100}")
    private int loginMaxQueue;
    @Value("${log.loginLog.keepAlive:120}")
    private int loginKeepAlive;

    @Bean("asyncSysLogExecutor")
    public Executor asyncSysLogExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(sysCorePoolSize);
        //最大线程数
        executor.setMaxPoolSize(sysMaxPoolSize);
        //队列大小
        executor.setQueueCapacity(sysMaxQueue);
        //线程池中的线程的名称前缀
        executor.setThreadNamePrefix("sys-user-log");
        //回收时间设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(sysKeepAlive);
        // 当pool已经达到max size的时候
        // 不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean("asyncLoginLogExecutor")
    public Executor asyncLoginLogExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(loginCorePoolSize);
        //最大线程数
        executor.setMaxPoolSize(loginMaxPoolSize);
        //队列大小
        executor.setQueueCapacity(loginMaxQueue);
        //线程池中的线程的名称前缀
        executor.setThreadNamePrefix("login-log");
        //回收时间设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(loginKeepAlive);
        // 当pool已经达到max size的时候
        // 不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
