package com.micro.darren.gateway.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
@Getter
@Setter
public class ExecuteThread implements Runnable{

    private String orderId;

    public ExecuteThread(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        log.info("正在处理的订单号:{}", orderId);
    }



}
