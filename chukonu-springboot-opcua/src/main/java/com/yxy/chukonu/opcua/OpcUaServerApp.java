package com.yxy.chukonu.opcua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpcUaServerApp {
	public static void main(String[] args) {
		SpringApplication.run(OpcUaServerApp.class, args);
	}
}