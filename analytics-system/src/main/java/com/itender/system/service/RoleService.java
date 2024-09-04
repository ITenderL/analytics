package com.itender.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.system.entity.Role;
import com.itender.system.vo.query.RoleQueryVO;

import java.util.List;

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
     * 保存角色权限关系
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    boolean saveRolePermission(Long roleId, List<Long> permissionIds);

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

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    boolean deleteRoleById(Long id);
}
