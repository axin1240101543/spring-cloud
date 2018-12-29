package com.micro.darren.gateway.service.impl;


import com.micro.darren.gateway.dao.read.ApiInfoReaderDao;
import com.micro.darren.gateway.entity.ApiInfo;
import com.micro.darren.gateway.service.ApiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiInfoServiceImpl implements ApiInfoService{

    @Autowired
    private ApiInfoReaderDao apiInfoReaderDao;

    @Override
    public List<ApiInfo> getApiInfoList() {
        Map<String, Object> params = new HashMap<>(8);
        params.put("status", 64);
        return apiInfoReaderDao.getApiInfoList(params);
    }

    @Override
    public List<ApiInfo> getApiInfoListToClass() {
        return apiInfoReaderDao.getApiInfoListToClass();
    }

    @Override
    public List<ApiInfo> getApiInfoListToClassByType(String type) {
        return apiInfoReaderDao.getApiInfoListToClassByType(type);
    }

    @Override
    public List<ApiInfo> getApiInfoListToClassResult() {
        return apiInfoReaderDao.getApiInfoListToClassResult();
    }

    @Override
    public ApiInfo getApiInfoListToClassById(Long id) {
        return apiInfoReaderDao.getApiInfoListToClassById(id);
    }

    @Override
    public List<ApiInfo> getgetApiInfoListToClassByTypeAndName(String type, String name) {
        return apiInfoReaderDao.getgetApiInfoListToClassByTypeAndName(type, name);
    }
}