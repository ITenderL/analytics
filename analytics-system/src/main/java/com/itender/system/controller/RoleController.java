package com.itender.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itender.system.entity.Result;
import com.itender.system.entity.Role;
import com.itender.system.service.RoleService;
import com.itender.system.vo.query.RoleQueryVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 分页查询角色列表
     *
     * @param roleQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result<IPage<Role>> list(RoleQueryVO roleQueryVo) {
        // 创建分页对象
        IPage<Role> page = new Page<>
                (roleQueryVo.getPageNo(), roleQueryVo.getPageSize());
        // 调用分页查询方法
        roleService.findRoleListByUserId(page, roleQueryVo);
        // 返回数据
        return Result.success(page);
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @PostMapping("/add")
    public Result<Object> add(@RequestBody Role role) {
        if (roleService.save(role)) {
            return Result.success().message("角色添加成功");
        }
        return Result.error().message("角色添加失败");
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Role role) {
        if (roleService.updateById(role)) {
            return Result.success().message("角色修改成功");
        }
        return Result.error().message("角色修改失败");
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        if (roleService.removeById(id)) {
            return Result.success().message("角色删除成功");
        }
        return Result.error().message("角色删除失败");
    }

    /**
     * 检查用户角色是否被使用
     * @param id
     * @return
     */
    // @PreAuthorize("hasAuthority('sys:role:delete')")
    @GetMapping("/check/{id}")
    public Result<Object> check(@PathVariable Long id){
        //调用检查用户角色是否被使用的方法
        if(roleService.hashRoleCount(id)){
            return Result.exist().message("该角色已分配给其他用户使用，无法删除");
        }
        return Result.success();
    }
}

