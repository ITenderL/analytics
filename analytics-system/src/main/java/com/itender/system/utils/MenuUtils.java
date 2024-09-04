package com.itender.system.utils;

import com.itender.system.entity.Permission;
import com.itender.system.vo.RouterVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author analytics
 * @date 2024/9/2 18:44
 * @description
 */
public class MenuUtils {

    /**
     * 生成路由
     *
     * @param menuList 菜单列表
     * @param pid      父菜单ID
     * @return
     */
    public static List<RouterVO> makeRouter(List<Permission> menuList, Long pid) {
        //创建集合保存路由信息
        List<RouterVO> routerVoList = new ArrayList<>();
        //判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                // 筛选不为空的菜单及与菜单父ID相同的数据
                .stream().filter(item -> Objects.nonNull(item) && item.getParentId().equals(pid))
                .forEach(item -> {
                    // 创建路由信息对象
                    RouterVO routerVo = new RouterVO();
                    routerVo.setName(item.getName());//路由名称
                    routerVo.setPath(item.getPath());//路由地址
                    //判断当前菜单是否是一级菜单
                    if (item.getParentId() == 0L) {
                        routerVo.setComponent("Layout");//一级菜单组件
                        routerVo.setAlwaysShow(true);//显示路由
                    } else {
                        routerVo.setComponent(item.getUrl());//具体某一个组件
                        routerVo.setAlwaysShow(false);//折叠路由
                    }
                    //设置Meta信息
                    routerVo.setMeta(routerVo.new Meta(item.getLabel(), item.getIcon(), item.getCode().split(",")));
                    //递归生成路由
                    List<RouterVO> children = makeRouter(menuList, item.getId());//子菜单
                    routerVo.setChildren(children);//设置子路由到路由对象中
                    //将路由信息添加到集合中
                    routerVoList.add(routerVo);
                });
        //返回路由信息
        return routerVoList;
    }

    /**
     * 生成菜单树
     *
     * @param menuList
     * @param pid
     * @return
     */
    public static List<Permission> makeMenuTree(List<Permission> menuList, Long pid) {
        //创建集合保存菜单数据
        List<Permission> permissionList = new ArrayList<>();
        //判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    //创建权限菜单对象
                    Permission permission = new Permission();
                    //将原有的属性复制给菜单对象
                    BeanUtils.copyProperties(item, permission);
                    //获取每一个item对象的子菜单，递归生成菜单树
                    List<Permission> children = makeMenuTree(menuList, item.getId());
                    //设置子菜单
                    permission.setChildren(children);
                    //将菜单对象添加到集合
                    permissionList.add(permission);
                });
        //返回菜单信息
        return permissionList;
    }
}
