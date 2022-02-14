package com.apitest.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//DB 연결 config
@Configuration
public class DataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource") // 이 형태의 모든 prefix로 읽어오게하기
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
