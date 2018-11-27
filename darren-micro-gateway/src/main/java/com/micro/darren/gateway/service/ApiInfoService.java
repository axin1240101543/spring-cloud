package com.micro.darren.gateway.service;


import com.micro.darren.gateway.entity.ApiInfo;
import java.util.List;

public interface ApiInfoService {

    /**
     * 获取有效接口信息
     * @return
     */
    List<ApiInfo> getApiInfoList();

}