package com.itender.analytics.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 限流，根据ip进行限流
     *
     * @return
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String ip = exchange.getRequest().getRemoteAddress().getHostString();
            //获取远程客户端IP
            String hostName = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            log.info("hostName: {}, ip: {}" , hostName, ip);
            return Mono.just(ip);
        };
    }
}
