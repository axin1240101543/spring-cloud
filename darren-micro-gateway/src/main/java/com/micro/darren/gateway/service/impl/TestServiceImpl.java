package com.micro.darren.gateway.service.impl;

import com.micro.darren.gateway.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @Async 会优先查找taskExecutor，也可以通过value来指定线程池，当没有配置线程池时，默认使用SimpleAsyncTaskExecutor
 * https://www.cnblogs.com/yw0219/p/8810956.html
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService{

    @Async
    @Override
    public void nonReturn() {
        log.info("test thread pool task executor");
    }

    @Override
    public ListenableFuture<String> asyncResult(String params) {
        return new AsyncResult<>(params);
    }
}
