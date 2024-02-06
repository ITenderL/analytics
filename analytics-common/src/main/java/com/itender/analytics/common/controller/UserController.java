package com.itender.analytics.common.controller;


import com.itender.analytics.common.entity.User;
import com.itender.analytics.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yuanhewei
 * @date 2024/1/20 11:50
 * @description
 */
@RestController
@RequestMapping ("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
