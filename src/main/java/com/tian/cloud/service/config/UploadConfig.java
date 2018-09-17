package com.tian.cloud.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianguang
 * 2018/9/17 下午3:41
 **/
@Configuration
@ConfigurationProperties(prefix = "upload")
@Data
public class UploadConfig {

    private String filePath;
}
