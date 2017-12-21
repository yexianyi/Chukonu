package net.chukonu.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = "net.chukonu.spring.boot.*")  
public class ChukonuSpringBootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChukonuSpringBootApplication.class, args);
    }
}
