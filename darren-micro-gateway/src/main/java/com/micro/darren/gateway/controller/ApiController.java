package com.micro.darren.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.micro.darren.gateway.entity.ApiInfo;
import com.micro.darren.gateway.service.ApiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {
	
	@Autowired
	private ApiInfoService apiInfoService;

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


}