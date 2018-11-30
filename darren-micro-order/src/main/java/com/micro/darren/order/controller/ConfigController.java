package com.micro.darren.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {

    @Value("${server.port}")
    private String port;

    @Value("${eureka.client.service-url.defaultZone}")
    private String defaultZone;

    @Value("${test}")
    private String test;

    @GetMapping("/getConfig")
    public String getConfig() {
        return "获取配置内容：" + port + " " + defaultZone;
    }

    @GetMapping("/test")
    public String test() {
        return test;
    }

}
