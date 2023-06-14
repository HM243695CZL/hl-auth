package com.hl.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.hl.admin.mapper")
@SpringBootApplication
public class HlAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HlAuthApplication.class, args);
    }

}