package com.micro.darren.user.service.impl;

import com.micro.darren.user.dao.read.UsersReaderDao;
import com.micro.darren.user.entity.User;
import com.micro.darren.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersReaderDao usersReaderDao;

    @Override
    public List<User> getUsers() {
        return usersReaderDao.getUsers();
    }
}
