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

    private String db;

    private String user;

    private String password;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
