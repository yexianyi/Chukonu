package com.yxy.chukonu.spring.boot.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.yxy.chukonu.*")  
public class RedisApplication 
{
	 public static void main(String[] args) throws Exception {
	        SpringApplication.run(RedisApplication.class, args);
	    }
}
