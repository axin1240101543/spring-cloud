package com.micro.darren.gateway.dao.read;

import com.micro.darren.gateway.entity.ApiInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApiInfoReaderDao {
    ApiInfo selectByPrimaryKey(Long id);

    /**
     * 获取有效接口信息
     * @param params
     * @return
     */
    List<ApiInfo> getApiInfoList(Map<String, Object> params);
}