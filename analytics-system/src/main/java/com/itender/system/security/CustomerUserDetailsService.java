package com.itender.system.security;

import com.itender.system.entity.Permission;
import com.itender.system.entity.User;
import com.itender.system.service.PermissionService;
import com.itender.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author analytics
 * @date 2024/8/31 21:29
 * @description 用户认证处理器类
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userService.findUserByUserName(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 查询用户权限列表
        List<Permission> permissionList = permissionService.findPermissionListByUserId(user.getId());
        // 获取权限的code
        String[] permissionCodeArray = permissionList.stream()
                .filter(Objects::nonNull)
                .map(Permission::getCode)
                .filter(StringUtils::isNotBlank).toArray(String[]::new);
        // 获取权限列表
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(permissionCodeArray);
        user.setAuthorities(authorityList);
        user.setPermissionList(permissionList);
        return user;
    }
}
