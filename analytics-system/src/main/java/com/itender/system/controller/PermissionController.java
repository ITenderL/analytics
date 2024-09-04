package com.itender.system.controller;


import com.itender.system.entity.Permission;
import com.itender.system.entity.Result;
import com.itender.system.service.PermissionService;
import com.itender.system.vo.RolePermissionVO;
import com.itender.system.vo.query.PermissionQueryVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 查询菜单列表
     *
     * @param permissionQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result list(PermissionQueryVO permissionQueryVo) {
        //调用查询菜单列表的方法
        List<Permission> permissionList = permissionService.findPermissionList(permissionQueryVo);
        //返回数据
        return Result.success(permissionList);
    }

    /**
     * 查询上级菜单列表
     *
     * @return
     */
    @GetMapping("/parent/list")
    public Result<List<Permission>> getParentList() {
        //调用查询上级菜单列表的方法
        List<Permission> permissionList = permissionService.findParentPermissionList();
        //返回数据
        return Result.success(permissionList);
    }

    /**
     * 添加菜单
     *
     * @param permission
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission) {
        //调用新增的方法
        if (permissionService.save(permission)) {
            return Result.success().message("菜单添加成功");
        }
        return Result.error().message("菜单添加失败");
    }

    /**
     * 修改菜单
     *
     * @param permission
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    @PutMapping("/update")
    public Result update(@RequestBody Permission permission) {
        //调用修改的方法
        if (permissionService.updateById(permission)) {
            return Result.success().message("菜单修改成功");
        }
        return Result.error().message("菜单修改失败");
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        //调用删除的方法
        if (permissionService.removeById(id)) {
            return Result.success().message("菜单删除成功");
        }
        return Result.error().message("菜单删除失败");
    }

    /**
     * 检查菜单是否有子菜单
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id) {
        //判断菜单是否有子菜单
        if (permissionService.hasChildrenOfPermission(id)) {
            return Result.exist().message("该菜单下有子菜单，无法删除");
        }
        return Result.success();
    }
}

