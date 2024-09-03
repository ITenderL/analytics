package com.itender.system.security;

import com.itender.system.security.filter.CheckTokenFilter;
import com.itender.system.security.handler.CustomerAuthenticationEntryPointHandler;
import com.itender.system.security.handler.CustomerAccessDeniedHandler;
import com.itender.system.security.handler.LoginFailureHandler;
import com.itender.system.security.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author analytics
 * @date 2024/8/31 22:35
 * @description
 */
@Configuration
// @EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private CustomerAuthenticationEntryPointHandler anonymousAuthenticationHandler;
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;
    @Resource
    private CustomerUserDetailsService customerUserDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private CheckTokenFilter checkTokenFilter;

    /**
     * 配置认证处理器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 处理登录认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http
                // 表单登录
                .formLogin()
                // 登录页面
                .loginProcessingUrl("/api/user/login")
                // 认证成功处理器
                .successHandler(loginSuccessHandler)
                // 认证失败处理器
                .failureHandler(loginFailureHandler)
                .and().csrf().disable()
                // 不创建session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 设置需要拦截的请求
                .and()
                .authorizeRequests().antMatchers("/api/user/login").permitAll()
                // 除上面之外的请求全部需要鉴权
                .anyRequest().authenticated()
                // 配置不需要鉴权的接口
                .and()
                .exceptionHandling()
                // // 匿名用户无权限处理器
                .authenticationEntryPoint(anonymousAuthenticationHandler)
                // // 认证无权限处理器
                .accessDeniedHandler(customerAccessDeniedHandler)
                // 跨域配置
                .and()
                .cors();
    }
}
