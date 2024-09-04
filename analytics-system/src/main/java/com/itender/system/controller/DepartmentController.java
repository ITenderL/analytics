package com.itender.system.controller;


import com.itender.system.entity.Department;
import com.itender.system.entity.Result;
import com.itender.system.service.DepartmentService;
import com.itender.system.vo.query.DepartmentQueryVO;
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
@RequestMapping("/api/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 查询部门列表
     *
     * @param departmentQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result list(DepartmentQueryVO departmentQueryVo) {
        //调用查询部门列表方法
        List<Department> departmentList = departmentService.findDepartmentList(departmentQueryVo);
        //返回数据
        return Result.success(departmentList);
    }

    /**
     * 查询上级部门列表
     *
     * @return
     */
    @GetMapping("/parent/list")
    public Result getParentDepartment() {
        // 调用查询上级部门列表方法
        List<Department> departmentList = departmentService.findParentDepartment();
        // 返回数据
        return Result.success(departmentList);
    }

    /**
     * 添加部门
     *
     * @param department
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    // @PreAuthorize("hasAuthority('sys:department:add')")
    @PostMapping("/add")
    public Result add(@RequestBody Department department) {
        if (departmentService.save(department)) {
            return Result.success().message("部门添加成功");
        }
        return Result.error().message("部门添加失败");
    }

    /**
     * 修改部门
     *
     * @param department
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    // @PreAuthorize("hasAuthority('sys:department:edit')")
    @PutMapping("/update")
    public Result update(@RequestBody Department department) {
        if (departmentService.updateById(department)) {
            return Result.success().message("部门修改成功");
        }
        return Result.error().message("部门修改失败");
    }

    /**
     * 查询某个部门下是否存在子部门
     *
     * @param id
     * @return
     */
    // @PreAuthorize("hasAuthority('sys:department:delete')")
    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id) {
        //调用查询部门下是否存在子部门的方法
        if (departmentService.hasChildrenOfDepartment(id)) {
            return Result.exist().message("该部门下存在子部门，无法删除");
        }
        //调用查询部门下是否存在用户的方法
        if (departmentService.hasUserOfDepartment(id)) {
            return Result.exist().message("该部门下存在用户，无法删除");
        }
        return Result.success();
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    // @PreAuthorize("hasAuthority('sys:department:delete')")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        if (departmentService.removeById(id)) {
            return Result.success().message("部门删除成功");
        }
        return Result.error().message("部门删除失败");
    }
}

