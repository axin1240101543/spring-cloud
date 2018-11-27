package com.micro.darren.user.dao.read;

import com.micro.darren.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersReaderDao {
    User selectByPrimaryKey(Integer uid);

    /**
     * 获取所有用户列表
     * @return
     */
    List<User> getUsers();
}