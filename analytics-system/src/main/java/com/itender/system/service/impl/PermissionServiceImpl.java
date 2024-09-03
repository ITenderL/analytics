package com.itender.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.system.entity.Permission;
import com.itender.system.entity.User;
import com.itender.system.mapper.PermissionMapper;
import com.itender.system.mapper.UserMapper;
import com.itender.system.service.PermissionService;
import com.itender.system.utils.MenuUtils;
import com.itender.system.vo.RolePermissionVo;
import com.itender.system.vo.query.PermissionQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Permission> findPermissionListByUserId(Long userId) {
        return permissionMapper.findPermissionListByUserId(userId);
    }

    /**
     * 查询菜单列表
     *
     * @param permissionQueryVo
     * @return
     */
    @Override
    public List<Permission> findPermissionList(PermissionQueryVo permissionQueryVo) {
        //创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        //设置排序自动
        queryWrapper.orderByAsc("order_num");
        //调用查询菜单列表的方法
        List<Permission> permissionList = baseMapper.selectList(queryWrapper);
        //生成菜单树
        return MenuUtils.makeMenuTree(permissionList, 0L);
    }

    /**
     * 查询上级菜单列表
     *
     * @return
     */
    @Override
    public List<Permission> findParentPermissionList() {
        //创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //只查询目录(0)和菜单(1)的数据
        queryWrapper.in("type", Arrays.asList(0, 1));
        queryWrapper.eq("is_delete", 0);
        //设置排序自动
        queryWrapper.orderByAsc("order_num");
        //调用查询菜单列表的方法
        List<Permission> permissionList = baseMapper.selectList(queryWrapper);
        //构造顶级菜单对象
        Permission permission = new Permission();
        permission.setId(0L);
        permission.setParentId(-1L);
        permission.setLabel("顶级菜单");
        permissionList.add(permission);
        //生成菜单数据
        return MenuUtils.makeMenuTree(permissionList, -1L);
    }

    /**
     * 检查菜单是否有子菜单
     *
     * @param id
     * @return
     */
    @Override
    public boolean hasChildrenOfPermission(Long id) {
        // 创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        // 查询父级ID
        queryWrapper.eq("parent_id", id);
        queryWrapper.eq("is_delete", 0);
        // 判断数量是否大于0，如果大于0则表示存在
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 查询权限菜单树
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public RolePermissionVo findPermissionTree(Long userId, Long roleId) {
        // 1.查询当前登录用户信息
        User user = userMapper.selectById(userId);
        // 创建集合保存权限菜单
        List<Permission> list = null;
        // 2.判断当前登录用户是否是管理员，如果是管理员，则查询所有的权限信息；如果不是管理员，则需要根据当前用户Id查询出当前用户所拥有的权限信息。
        if (user != null && !ObjectUtils.isEmpty(user.getIsAdmin()) && user.getIsAdmin() == 1) {
            // 查询出所有的权限菜单
            list = baseMapper.selectList(null);
        } else {
            // 根据当前用户Id查询出当前用户所拥有的权限信息
            list = baseMapper.findPermissionListByUserId(userId);
        }
        // 3.将登录用户所拥有的权限封装成菜单树
        List<Permission> permissionList = MenuUtils.makeMenuTree(list, 0L);
        // 4.查询要分配角色拥有的权限列表
        List<Permission> rolePermissions = baseMapper.findPermissionListByRoleId(roleId);
        // 创建集合保存权限ID
        List<Long> listIds = new ArrayList<Long>();
        // 进行数据回显
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    Optional.ofNullable(rolePermissions).orElse(new ArrayList<>())
                            .stream()
                            .filter(Objects::nonNull)
                            .forEach(obj -> {
                                // 判断两者的权限ID是否一致，如果一致，则表示拥有该权限
                                if (item.getId().equals(obj.getId())) {
                                    // 将权限ID保存到集合中
                                    listIds.add(obj.getId());
                                    return;
                                }
                            });
                });
        // 创建RolePermissionVo类对象
        return new RolePermissionVo(permissionList, listIds.toArray());
    }
}
