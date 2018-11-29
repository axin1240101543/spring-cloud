package com.micro.darren.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.*;

@Component
@Slf4j
public class ThreadPoolManager implements BeanFactoryAware {

    /**
     * 线程池的一些配置
     */
    private final int corePoolSize = 2;
    private final int maxPoolSize = 10;
    private final int keepAliveSeconds = 30;
    private final int queueCapacity = 50;
    Queue<Object> queue = new LinkedBlockingQueue<>();
    private BeanFactory beanFactory;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 配置拒绝策略：即线程数大于最大线程数时的处理
     */
    final RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            String orderId = ((ExecuteThread) r).getOrderId();
            log.info("加入队列的订单号:{}", orderId);
            queue.offer(orderId);//将订单加入缓存队列
        }
    };

    /**
     * 新建一个线程池
     */
    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(this.corePoolSize, this.maxPoolSize, this.keepAliveSeconds, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(this.queueCapacity), this.rejectedExecutionHandler);
    /**
     * 新建一个调度线程池
     */
    final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    /**
     * 添加订单
     *
     * @param orderId
     */
    public void saveOrder(String orderId) {
        log.info("ready add thread pool process order");
        if (null == stringRedisTemplate.opsForValue().get(orderId)) {
            stringRedisTemplate.opsForValue().set(orderId, "");
            ExecuteThread executeThread = new ExecuteThread(orderId);
            threadPoolExecutor.execute(executeThread);//执行目标任务
        }
    }

    final ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            if (!queue.isEmpty()) {
                if (threadPoolExecutor.getQueue().size() < queueCapacity) {
                    String orderId = (String) queue.poll();
                    log.info("缓存队列执行订单号:{}", orderId);
                    ExecuteThread executeThread = new ExecuteThread(orderId);
                    threadPoolExecutor.execute(executeThread);//执行目标任务
                }
            }
        }
    }, 0, 1, TimeUnit.SECONDS);

}
