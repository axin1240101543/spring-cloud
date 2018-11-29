package com.micro.darren.gateway.controller;

import com.micro.darren.gateway.config.ThreadPoolManager;
import com.micro.darren.gateway.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class TestController {

    /**
     * 测试线程池
     */
    @Autowired
    private TestService testService;

    /**
     * 测试线程池 + 队列
     */
    @Autowired
    private ThreadPoolManager threadPoolManager;

    @GetMapping("/test")
    public void test(){
        testService.nonReturn();
    }

    @GetMapping("/test1")
    public void test1() throws ExecutionException, InterruptedException, TimeoutException {
        ListenableFuture listenableFuture =  testService.asyncResult("123456");
        String  str = (String) listenableFuture.get();//阻塞调用
        log.info("str:{}", str);
        String str1 = (String) listenableFuture.get(10, TimeUnit.SECONDS);//限时10秒调用
        log.info("str1:{}", str1);
    }


    @GetMapping("/test2")
    public void test2(){
        String orderId = UUID.randomUUID().toString();
        threadPoolManager.saveOrder(orderId);
    }



}
