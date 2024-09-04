package com.itender.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itender.system.entity.Role;
import org.apache.ibatis.annotations.Select;

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
    @Select("select count(1) from sys_user_role where role_Id = #{roleId}")
    int getRoleCountByRoleId(Long id);
}
