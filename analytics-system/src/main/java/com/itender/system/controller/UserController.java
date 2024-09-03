package com.itender.system.controller;


import com.itender.system.entity.Result;
import com.itender.system.entity.User;
import com.itender.system.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public Result<List<User>> list() {
        return Result.success(userService.getUsers());
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id) {
        return Result.success(userService.getUserById(id));
    }
}

