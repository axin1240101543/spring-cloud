package com.micro.darren.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 配置线程池
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(50);//核心线程数
        threadPoolTaskExecutor.setMaxPoolSize(200);//最大线程数
        threadPoolTaskExecutor.setQueueCapacity(5000);//队列容量
        threadPoolTaskExecutor.setKeepAliveSeconds(30000);//线程活跃时间（秒）
        //threadPoolTaskExecutor.setThreadNamePrefix("");//线程名称前缀
        //threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//拒绝策略：主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
        return threadPoolTaskExecutor;
    }

}
