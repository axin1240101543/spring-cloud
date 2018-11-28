package com.micro.darren.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.micro.darren.gateway.entity.ApiInfo;
import com.micro.darren.gateway.service.ApiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
	
	@Autowired
	private ApiInfoService apiInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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


}