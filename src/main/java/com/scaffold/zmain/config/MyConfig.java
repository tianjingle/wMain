package com.scaffold.zmain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author tianjl
 * @Date 2021/10/27 15:05
 * @Discription disc
 */
@Configuration
@ConfigurationProperties(prefix = "com.scaffold")
public class MyConfig {

    private String basePath;

    private String gudong;

    public String getGudong() {
        return gudong;
    }

    public void setGudong(String gudong) {
        this.gudong = gudong;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
