package com.micro.darren.gateway.service;


import com.micro.darren.gateway.entity.ApiInfo;
import java.util.List;

public interface ApiInfoService {

    /**
     * 获取有效接口信息
     * @return
     */
    List<ApiInfo> getApiInfoList();


    /**
     * 测试无参的@select
     * @return
     */
    List<ApiInfo> getApiInfoListToClass();

    /**
     * 测试带参的@select
     * @param type
     * @return
     */
    List<ApiInfo> getApiInfoListToClassByType(String type);

    /**
     * 测试@results
     * @return
     */
    List<ApiInfo> getApiInfoListToClassResult();

    /**
     * 测试无参的@selectProvider
     * @param id
     * @return
     */
    ApiInfo getApiInfoListToClassById(Long id);

    /**
     * 测试带参的@selectProvider
     * @param type
     * @param name
     * @return
     */
    List<ApiInfo> getgetApiInfoListToClassByTypeAndName(String type, String name);

}