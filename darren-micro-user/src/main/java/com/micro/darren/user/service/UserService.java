package com.micro.darren.user.service;

import com.micro.darren.user.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 获取所有用户列表
     * @return
     */
    List<User> getUsers();

}
