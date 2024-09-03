package com.itender.system.service;

import com.itender.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
public interface UserService extends IService<User> {

    /**
     * 获取用户列表
     *
     * @return
     */
    List<User> getUsers();

    /**
     * 根据Id查询用户
     *
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    User findUserByUserName(String username);
}
