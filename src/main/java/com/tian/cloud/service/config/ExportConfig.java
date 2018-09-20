package com.tian.cloud.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianguang
 * 2018/9/6 下午4:02
 **/
@Configuration
@ConfigurationProperties(prefix = "export")
@Data
public class ExportConfig {

    private String filePath;

    public String getTempPath() {
        return filePath + "temp/";
    }
}
