package com.micro.darren.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.micro.darren.user.entity.User;
import com.micro.darren.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    @ResponseBody
    public String getUsers(){
        List<User> users = userService.getUsers();
        return JSONObject.toJSONString(users);
    }

}
