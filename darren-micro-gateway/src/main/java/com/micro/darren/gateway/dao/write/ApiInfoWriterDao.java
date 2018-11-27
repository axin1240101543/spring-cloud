package com.micro.darren.gateway.dao.write;

import com.micro.darren.gateway.entity.ApiInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiInfoWriterDao {
    int deleteByPrimaryKey(Long id);

    int insert(ApiInfo record);

    int insertSelective(ApiInfo record);

    int updateByPrimaryKeySelective(ApiInfo record);

    int updateByPrimaryKeyWithBLOBs(ApiInfo record);

    int updateByPrimaryKey(ApiInfo record);
}