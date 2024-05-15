package com.itender.analytics.search.config;

import cn.hutool.core.util.StrUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author yuanhewei
 * @date 2024/5/14 16:18
 * @description
 */
@Configuration
public class EsClientConfig {
    @Value("${elasticsearch.rest.uris}")
    private String uris;

    @Bean
    public RestHighLevelClient getRestHighLevelClient() {
        HttpHost[] httpHosts = Arrays.stream(uris.split(","))
                .filter(StrUtil::isNotBlank)
                .map(url -> new HttpHost(url.split(":")[0], Integer.parseInt(url.split(":")[1]), "http"))
                .toArray(HttpHost[]::new);
        // ConnectTimeout: 设置连接超时时间，单位毫秒。SocketTimeout：请求获取数据的超时时间，单位毫秒。
        RestClientBuilder restClientBuilder = RestClient
                .builder(httpHosts)
                .setRequestConfigCallback(builder ->
                        builder.setConnectTimeout(5000)
                                .setSocketTimeout(60000)
                );
        return new RestHighLevelClient(restClientBuilder);
    }
}
