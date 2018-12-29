package com.micro.darren.gateway.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 动态生成sql   不使用xml文件配置
 */
public class ApiInfoSqlProvider {

    public String getApiInfoListToClassById(Long id){
        return "select * from drug_api_info where id = #{id}";
    }

    public String getgetApiInfoListToClassByTypeAndName(@Param("type")String type, @Param("name")String name){
        return new SQL(){
            {
                SELECT("*");
                FROM("drug_api_info");
                if (type != null && name != null)
                    WHERE("type = #{type} and name = #{name}");
            }
        }.toString();
    }

}
