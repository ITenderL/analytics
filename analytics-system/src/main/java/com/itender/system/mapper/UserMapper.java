package com.itender.system.mapper;

import com.itender.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 删除用户角色关系
     *
     * @param userId
     * @return
     */
    int deleteUserRole(Long userId);

    /**
     * 添加用户角色关系
     *
     * @param userId
     * @param roleIds
     * @return
     */
    int saveUserRole(Long userId, List<Long> roleIds);
}
