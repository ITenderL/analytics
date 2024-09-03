package com.itender.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class AnalyticsSystemApplicationTests {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    void contextLoads() {
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

}
