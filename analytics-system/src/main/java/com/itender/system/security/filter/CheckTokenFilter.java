package com.itender.system.security.filter;

import com.itender.system.exception.CustomerAuthenticationException;
import com.itender.system.security.CustomerUserDetailsService;
import com.itender.system.security.handler.LoginFailureHandler;
import com.itender.system.service.RedisService;
import com.itender.system.utils.JwtUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.itender.system.constants.RedisConstants.AUTH_TOKEN;
import static com.itender.system.enums.BizExceptionEnum.*;

/**
 * @author analytics
 * @date 2024/9/1 22:28
 * @description
 */
@Slf4j
@Getter
@Component
public class CheckTokenFilter extends OncePerRequestFilter {

    @Value("${request.login.url}")
    private String loginUrl;

    @Resource
    private RedisService redisService;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private CustomerUserDetailsService customerUserDetailsService;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 登录不需要token
            String requestUri = request.getRequestURI();
            if (requestUri.equals(loginUrl)) {
                filterChain.doFilter(request, response);
                return;
            }
            validaToken(request);
        } catch (AuthenticationException e) {
            log.error("登录异常", e);
            loginFailureHandler.onAuthenticationFailure(request, response, e);
        }
        // 登录请求不需要携带token，可以直接方向
        doFilter(request, response, filterChain);
    }

    /**
     * 验证token
     *
     * @param request
     */
    private void validaToken(HttpServletRequest request) {
        // 1.获取请求头中获取
        String token = request.getHeader("token");
        // 2.判断token是否存在，如果不存在去请求参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 3.如果请求参数中没有token，则抛出异常
        if (StringUtils.isBlank(token)) {
            throw new CustomerAuthenticationException(TOKEN_IS_NULL.getMsg());
        }
        // 4.判断redis中是否存在token
        String redisTokenKey = AUTH_TOKEN + token;
        String redisToken = redisService.get(redisTokenKey);
        if (StringUtils.isBlank(redisToken)) {
            throw new CustomerAuthenticationException(TOKEN_IS_EXPIRED.getMsg());
        }
        if (!redisToken.equals(token)) {
            throw new CustomerAuthenticationException(TOKEN_CHECK_FAILED.getMsg());
        }
        // 5. token存在，解析token
        String username = jwtUtils.getUsernameFromToken(token);
        if (StringUtils.isBlank(username)) {
            throw new CustomerAuthenticationException(TOKEN_CHECK_FAILED.getMsg());
        }
        // 6. token存在，获取用户信息
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (Objects.isNull(userDetails)) {
            throw new CustomerAuthenticationException(TOKEN_CHECK_FAILED.getMsg());
        }
        // 7. 创建用户身份认证对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 8. 设置请求信息
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 9. 将验证信息传给将SpringSecurity上线文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
