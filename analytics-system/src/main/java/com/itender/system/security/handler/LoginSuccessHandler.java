package com.itender.system.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itender.system.constants.ResultCode;
import com.itender.system.entity.LoginResult;
import com.itender.system.entity.User;
import com.itender.system.service.RedisService;
import com.itender.system.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.itender.system.constants.RedisConstants.AUTH_TOKEN;

/**
 * @author analytics
 * @date 2024/8/31 21:40
 * @description 登录认证成功处理器类
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //设置响应的编码格式
        response.setContentType("application/json;charset=utf-8");
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //获取当前登录用户信息
        User user = (User) authentication.getPrincipal();
        //生成token
        String token = jwtUtils.generateToken(user);
        //设置token的签名密钥及过期时间
        long expireTime = Jwts.parser()
                //设置前面密钥
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(token.replace("jwt_", ""))
                //设置过期时间
                .getBody().getExpiration().getTime();
        //创建LoginResult登录结果对象
        LoginResult loginResult = new LoginResult(user.getId(), ResultCode.SUCCESS, token, expireTime);
        //将对象转换成JSON格式，并消除循环引用
        String result = JSON.toJSONString(loginResult, SerializerFeature.DisableCircularReferenceDetect);
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        // 将token保存到redis中
        String redisKey = AUTH_TOKEN + token;
        redisService.set(redisKey, token, jwtUtils.getExpiration() / 1000);
    }
}
