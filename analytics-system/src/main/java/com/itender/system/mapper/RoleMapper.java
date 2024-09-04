package com.itender.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itender.system.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询角色数量
     *
     * @param id
     * @return
     */
    int getRoleCountByRoleId(Long id);

    /**
     * 删除角色权限关系
     *
     * @param roleId
     */
    void deleteRolePermission(Long roleId);

    /**
     * 删除角色权限关系
     *
     * @param id
     */
    void deleteRolePermissionByRoleId(Long id);

    /**
     * 保存角色权限关系
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
