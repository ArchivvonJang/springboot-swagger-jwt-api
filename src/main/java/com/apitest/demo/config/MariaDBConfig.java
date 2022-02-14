package com.apitest.demo.config;

import com.apitest.demo.interceptor.MybatissInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

//Maria DB Config
@Configuration
@MapperScan("com.apitest.demo.**.dao") //어떤 Mapper를 읽어올 것인지
public class MariaDBConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setPlugins(new MybatissInterceptor());
        sessionFactory.setDataSource(dataSource);

        //xml 파일을 읽을 수 있는 mapper location 을 지정
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mappers/*.xml")); //파일이 여러개 Resources

        //myBatis config도 읽을 수 있도록 지정
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");//단일 파일을 읽으므로 getResource()
        sessionFactory.setConfigLocation(myBatisConfig);


        return sessionFactory.getObject();
    }
}
