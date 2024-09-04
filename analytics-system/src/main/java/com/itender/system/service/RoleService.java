package com.itender.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.system.entity.Role;
import com.itender.system.vo.query.RoleQueryVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户查询角色列表
     *
     * @param page
     * @param roleQueryVo
     * @return
     */
    IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVO roleQueryVo);

    /**
     * 判断角色下是否有用户
     *
     * @param id
     * @return
     */
    boolean hashRoleCount(Long id);
}
