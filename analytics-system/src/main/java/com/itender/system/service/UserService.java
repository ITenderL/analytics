package com.itender.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.system.entity.User;
import com.itender.system.vo.query.UserQueryVO;

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


    /**
     * 分页查询用户列表
     *
     * @param page
     * @param userQueryVo
     * @return
     */
    IPage<User> findUserListByPage(IPage<User> page, UserQueryVO userQueryVo);

    /**
     * 根据Id删除用户
     *
     * @param userId
     * @return
     */
    boolean deleteById(Long userId);

    /**
     * 分配角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    boolean saveUserRole(Long userId, List<Long> roleIds);
}
