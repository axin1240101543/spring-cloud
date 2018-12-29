package com.micro.darren.gateway.dao.read;

import com.micro.darren.gateway.dao.sql.ApiInfoSqlProvider;
import com.micro.darren.gateway.entity.ApiInfo;
import org.apache.ibatis.annotations.*;

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


    @Select("select * from drug_api_info")
    List<ApiInfo> getApiInfoListToClass();

    @Select("select * from drug_api_info where type = #{type}")
    List<ApiInfo> getApiInfoListToClassByType(@Param("type") String type);

    /**
     * 使用Results来进行手动映射，符合驼峰关系的字段可以忽略。
     * @return
     */
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "name", column = "name"),
            @Result(property = "wsdl", column = "wsdl"),
            @Result(property = "level", column = "level"),
            @Result(property = "description", column = "description"),
    })
    @Select("select * from drug_api_info")
    List<ApiInfo> getApiInfoListToClassResult();

    /**
     * 使用selectProvider来指定工具类编写的sql方法  type：工具类  method：对应的方法
     * @param id
     * @return
     */
    @SelectProvider(type = ApiInfoSqlProvider.class, method = "getApiInfoListToClassById")
    ApiInfo getApiInfoListToClassById(Long id);

    @SelectProvider(type = ApiInfoSqlProvider.class, method = "getgetApiInfoListToClassByTypeAndName")
    List<ApiInfo> getgetApiInfoListToClassByTypeAndName(@Param("type") String type, @Param("name") String name);
}