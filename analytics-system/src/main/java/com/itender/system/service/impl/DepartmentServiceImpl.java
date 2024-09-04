package com.itender.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.system.entity.Department;
import com.itender.system.entity.User;
import com.itender.system.mapper.DepartmentMapper;
import com.itender.system.mapper.UserMapper;
import com.itender.system.service.DepartmentService;
import com.itender.system.utils.DepartmentUtils;
import com.itender.system.vo.query.DepartmentQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Resource
    private UserMapper userMapper;

    /**
     * 查询部门列表
     *
     * @param departmentQueryVo
     * @return
     */
    @Override
    public List<Department> findDepartmentList(DepartmentQueryVO departmentQueryVo) {
        //创建条件构造器对象
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        //部门名称
        queryWrapper.like(StringUtils.isNotBlank(departmentQueryVo.getDepartmentName()), "department_name", departmentQueryVo.getDepartmentName());
        queryWrapper.eq("is_delete", 0);
        //排序
        queryWrapper.orderByAsc("order_num");
        //查询部门列表
        List<Department> departmentList = baseMapper.selectList(queryWrapper);
        //生成部门树
        return DepartmentUtils.makeDepartmentTree(departmentList, 0L);
    }

    /**
     * 查询上级部门列表
     *
     * @return
     */
    @Override
    public List<Department> findParentDepartment() {
        //创建条件构造器对象
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        //排序
        queryWrapper.orderByAsc("order_num");
        //查询部门列表
        List<Department> departmentList = baseMapper.selectList(queryWrapper);
        //创建部门对象
        Department department = new Department();
        department.setId(0L);
        department.setDepartmentName("顶级部门");
        department.setPid(-1L);
        departmentList.add(department);
        //生成部门树列表
        //返回部门列表
        return DepartmentUtils.makeDepartmentTree(departmentList, -1L);
    }

    /**
     * 判断部门下是否有子部门
     *
     * @param id
     * @return
     */
    @Override
    public boolean hasChildrenOfDepartment(Long id) {
        //创建条件构造器对象
        QueryWrapper<Department> queryWrapper = new QueryWrapper<Department>();
        queryWrapper.eq("pid", id);
        //如果数量大于0，表示存在
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 判断部门下是否存在用户
     *
     * @param id
     * @return
     */
    @Override
    public boolean hasUserOfDepartment(Long id) {
        //创建条件构造器对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", id);
        //如果数量大于0，表示存在
        return userMapper.selectCount(queryWrapper) > 0;
    }
}
