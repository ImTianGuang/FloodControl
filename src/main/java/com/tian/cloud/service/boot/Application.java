package com.tian.cloud.service.boot;

import com.tian.cloud.service.interceptor.CommonInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.Resource;


@SpringBootApplication(scanBasePackages = "com.tian.cloud.service")
@MapperScan(value = "com.tian.cloud.service.dao.mapper")
@EnableAutoConfiguration
public class Application {



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
