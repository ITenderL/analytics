package com.itender.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itender.system.entity.User;
import com.itender.system.mapper.UserMapper;
import com.itender.system.service.UserService;
import com.itender.system.vo.query.UserQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.itender.system.constants.SystemConstants.DEFAULT_AVATAR;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itender
 * @since 2024-08-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findUserByUserName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    /**
     * 分页查询用户列表
     *
     * @param page
     * @param userQueryVo
     * @return
     */
    @Override
    public IPage<User> findUserListByPage(IPage<User> page, UserQueryVO userQueryVo) {
        // 创建条件构造器对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 部门编号
        queryWrapper.eq(!ObjectUtils.isEmpty(userQueryVo.getDepartmentId()), "department_id", userQueryVo.getDepartmentId());
        // 用户名
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getUsername()), "username", userQueryVo.getUsername());
        // 真实姓名
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getRealName()), "real_name", userQueryVo.getRealName());
        // 电话
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getPhone()), "phone", userQueryVo.getPhone());
        // 执行查询
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据Id删除用户
     *
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Long userId) {
        // 根据用户ID查询用户信息
        User user = baseMapper.selectById(userId);
        // 删除用户角色关系
        baseMapper.deleteUserRole(userId);
        // 删除用户信息
        if (baseMapper.deleteById(userId) > 0) {
            // 判断用户对象是的为空
            if (Objects.nonNull(user) && StringUtils.isNotBlank(user.getAvatar()) && Objects.equals(user.getAvatar(), DEFAULT_AVATAR)) {
                // 删除阿里云OSS中的文件
                // fileService.deleteFile(user.getAvatar());
            }
            return true;
        }
        return false;
    }

    /**
     * 保存用户角色关系
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUserRole(Long userId, List<Long> roleIds) {
        // 删除用户角色关系
        baseMapper.deleteUserRole(userId);
        // 保存用户角色关系
        return baseMapper.saveUserRole(userId, roleIds) > 0;
    }
}
