package com.tian.cloud.service.boot;

import com.tian.cloud.service.interceptor.CommonInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/10/13 上午9:27
 **/
@Configuration
@Slf4j
public class SpringMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("add interceptor:123456");
        registry.addInterceptor(new CommonInterceptor());
        super.addInterceptors(registry);
    }
}
