package com.micro.darren.gateway.controller;

import com.micro.darren.gateway.config.ThreadPoolManager;
import com.micro.darren.gateway.entity.Son;
import com.micro.darren.gateway.entity.UserVo;
import com.micro.darren.gateway.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * http://10.1.23.238:8080/getXML
     * @return
     */
    @GetMapping(value = "/getXML", produces = MediaType.APPLICATION_XML_VALUE)
    public UserVo getXML(){
        UserVo user = new UserVo();
        user.setId(10000L);
        user.setName("Darren");
        user.setAge(23);
        user.setMobile("1234578910");
        user.setAddress("深圳");
        Son son = new Son();
        son.setId(100001L);
        son.setName("Love");
        son.setAge(1);
        son.setAddress("深圳");
        List<Son> list = new ArrayList<>();
        list.add(son);
        user.setList(list);
        return user;
    }



}
