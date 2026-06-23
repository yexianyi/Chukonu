package com.yxy.chukonu.springboot.graalvm.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
        
        // 只保留这一行标准的 Spring Boot 启动命令即可
        SpringApplication.run(Application.class, args);
        
        long endTime = System.currentTimeMillis();
        System.out.println("=================================================");
        System.out.println("actual startup time: " + (endTime - startTime) + " ms");
        System.out.println("================================================="); }
}