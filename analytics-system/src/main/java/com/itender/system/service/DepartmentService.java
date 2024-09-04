package com.itender.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itender.system.entity.Department;
import com.itender.system.vo.query.DepartmentQueryVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
public interface DepartmentService extends IService<Department> {
    /**
     * 查询部门列表
     *
     * @param departmentQueryVo
     * @return
     */
    List<Department> findDepartmentList(DepartmentQueryVO departmentQueryVo);

    /**
     * 查询上级部门列表
     *
     * @return
     */
    List<Department> findParentDepartment();

    /**
     * 判断部门下是否有子部门
     *
     * @param id
     * @return
     */
    boolean hasChildrenOfDepartment(Long id);

    /**
     * 判断部门下是否存在用户
     *
     * @param id
     * @return
     */
    boolean hasUserOfDepartment(Long id);
}
