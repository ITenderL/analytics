package com.itender.system;

import com.itender.system.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class AnalyticsSystemApplicationTests {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private JwtUtils jwtUtils;
    @Test
    void contextLoads() {
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    @Test
    void test() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MjU3OTc3MTUsInN1YiI6ImFkbWluIiwiaWF0IjoxNzI1Nzk1MzE1OTY5fQ.cLvZ_q9mdmJQOYuWU3Z8ISzdTFlKswxk0cDB-O2sKcYj7v9B7tmNMZ2kfeepvn0ek9JSb83qXMTLF3mTKnCI_g";
        String usernameFromToken = jwtUtils.getUsernameFromToken(token);
        System.out.println(usernameFromToken);
    }

}
