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
}