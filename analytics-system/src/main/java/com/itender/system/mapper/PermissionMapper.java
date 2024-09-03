package com.itender.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itender.system.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户id查询权限菜单列表
     */
    List<Permission> findPermissionListByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询权限列表
     * @param roleId
     * @return
     */
    List<Permission> findPermissionListByRoleId(Long roleId);
}
