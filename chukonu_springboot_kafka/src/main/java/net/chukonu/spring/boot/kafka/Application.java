package net.chukonu.spring.boot.kafka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = "net.chukonu.spring.boot.kafka.*")
@EnableAsync
public class Application {

	
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		
		
	}


}
