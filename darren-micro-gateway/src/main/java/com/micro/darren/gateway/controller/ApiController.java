package com.micro.darren.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.micro.darren.gateway.entity.ApiInfo;
import com.micro.darren.gateway.service.ApiInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class ApiController {
	
	@Autowired
	private ApiInfoService apiInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String K = "test.";
    private static final String HK = "redis.hashkey";

    /**
     * 获取有效接口信息
     * @return
     */
	@GetMapping("/getApiInfoList")
    @ResponseBody
    public String getApiInfoList(){
        List<ApiInfo> list = apiInfoService.getApiInfoList();
        return JSONObject.toJSONString(list);
    }

    @GetMapping("/getRedisValue")
    @ResponseBody
    public String getRedisValue(){
        stringRedisTemplate.opsForValue().set("abc", "123456");
        String string = stringRedisTemplate.opsForValue().get("abc");
        return string;
    }

    @GetMapping("/getRedis")
    public void getRedis(){
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setCreateId((long)123456);
        apiInfo.setCreateTime(new Date());
        apiInfo.setDescription("test");
        redisTemplate.opsForHash().put(K, HK, apiInfo);
        ApiInfo info = (ApiInfo) redisTemplate.opsForHash().get(K, HK);
        log.info("info:{}", info);
    }


}