package com.itender.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.system.entity.Role;
import com.itender.system.entity.User;
import com.itender.system.mapper.RoleMapper;
import com.itender.system.mapper.UserMapper;
import com.itender.system.service.RoleService;
import com.itender.system.vo.query.RoleQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper baseMapper;

    /**
     * 保存角色权限
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRolePermission(Long roleId, List<Long> permissionIds) {
        // 删除该角色对应的权限信息
        baseMapper.deleteRolePermission(roleId);
        // 保存角色权限
        return baseMapper.saveRolePermission(roleId, permissionIds) > 0;
    }

    @Override
    public IPage<Role> findRoleListByUserId(IPage<Role> page, RoleQueryVO roleQueryVo) {
        // 创建条件构造器
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        // 角色名称
        queryWrapper.like(StringUtils.isNotBlank(roleQueryVo.getRoleName()),
                "role_name", roleQueryVo.getRoleName());
        // 排序
        queryWrapper.orderByAsc("id");
        // 根据用户ID查询用户信息
        User user = userMapper.selectById(roleQueryVo.getUserId());
        // 如果用户不为空、且不是管理员，则只能查询自己创建的角色 user.getIsAdmin() != 1
        if (Objects.nonNull(user) && Objects.nonNull(user.getIsAdmin())
                && !Objects.equals(1, user.getIsAdmin())) {
            queryWrapper.eq("create_user", roleQueryVo.getUserId());
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean hashRoleCount(Long id) {
        return baseMapper.getRoleCountByRoleId(id) > 0;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRoleById(Long id) {
        //删除角色权限关系
        baseMapper.deleteRolePermissionByRoleId(id);
        //删除角色
        return baseMapper.deleteById(id)>0;
    }
}
