package com.micro.darren.user.dao.write;

import com.micro.darren.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersWriterDao {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}