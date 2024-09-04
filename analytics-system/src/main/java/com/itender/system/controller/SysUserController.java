package com.itender.system.controller;


import com.itender.system.entity.Permission;
import com.itender.system.entity.Result;
import com.itender.system.entity.User;
import com.itender.system.entity.UserInfo;
import com.itender.system.service.RedisService;
import com.itender.system.utils.JwtUtils;
import com.itender.system.utils.MenuUtils;
import com.itender.system.vo.RouterVO;
import com.itender.system.vo.TokenVO;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.itender.system.constants.RedisConstants.AUTH_TOKEN;

@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {

    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisService redisService;

    /**
     * 刷新token
     *
     * @param request
     * @return
     */
    @PostMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        //从headers中获取token信息
        String token = request.getHeader("token");
        //判断headers头部是否存在token信息
        if (ObjectUtils.isEmpty(token)) {
            //从请求参数中获取token
            token = request.getParameter("token");
        }
        //从Spring Security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户身份信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //定义变量，保存新的token信息
        String newToken = "";
        //验证提交过来的token信息是否是合法的
        if (Boolean.TRUE.equals(jwtUtils.validateToken(token, userDetails))) {
            //重新生成新的token
            newToken = jwtUtils.refreshToken(token);
        }
        //获取本次token的到期时间
        long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(newToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        //清除原来的token信息
        String oldTokenKey = AUTH_TOKEN + token;
        redisService.del(oldTokenKey);
        //将新的token信息保存到缓存中
        String newTokenKey = AUTH_TOKEN + newToken;
        redisService.set(newTokenKey, newToken, jwtUtils.getExpiration() / 1000);
        //创建TokenVo对象
        TokenVO tokenVo = new TokenVO(expireTime, newToken);
        //返回数据
        return Result.success(tokenVo).message("token刷新成功");
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo() {
        //从Spring Security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //判断身份信息authentication是否为空
        if (authentication == null) {
            return Result.error().message("用户信息查询失败");
        }
        //获取用户信息
        User user = (User) authentication.getPrincipal();
        //获取该用户拥有的权限信息
        List<Permission> permissionList = user.getPermissionList();
        //获取权限编码
        Object[] roles = permissionList.stream().filter(Objects::nonNull).map(Permission::getCode).toArray();
        //创建用户信息
        UserInfo userInfo = new UserInfo(user.getId(), user.getNickName(), user.getAvatar(), null, roles);
        //返回数据
        return Result.success(userInfo);
    }

    /**
     * 获取登录用户的菜单数据
     *
     * @return
     */
    @GetMapping("/getMenuList")
    public Result getMenuList() {
        //从Spring Security上下文中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        User user = (User) authentication.getPrincipal();
        //获取该用户拥有的权限信息
        List<Permission> permissionList = user.getPermissionList();
        //筛选当前用户拥有的目录和菜单数据
        List<Permission> collect = permissionList.stream()
                // 只筛选目录和菜单数据，按钮不需要添加到路由菜单中
                // 0-目录 1-菜单  2-按钮
                .filter(item -> Objects.nonNull(item) && !Objects.equals(item.getType(), 2))
                .collect(Collectors.toList());
        //生成路由数据
        List<RouterVO> routerVoList = MenuUtils.makeRouter(collect, 0L);
        //返回数据
        return Result.success(routerVoList);
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        // 获取token信息
        String token = request.getHeader("token");
        // 如果头部中没有携带token，则从参数中获取
        if (ObjectUtils.isEmpty(token)) {
            // 从参数中获取token信息
            token = request.getParameter("token");
        }
        // 从Spring Security上下文对象中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 判断用户信息是否为空，如果不为空，则需要清空用户嘻嘻
        if (authentication != null) {
            // 清除用户信息
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            // 清除Redis缓存中的token
            redisService.del(AUTH_TOKEN + token);
            return Result.success();
        }
        return Result.error();
    }
}
