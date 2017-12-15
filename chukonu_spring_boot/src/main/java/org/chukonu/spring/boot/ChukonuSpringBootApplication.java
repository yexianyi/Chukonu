package org.chukonu.spring.boot;

import org.chukonu.spring.boot.controller.FunctionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAutoConfiguration
@EnableSwagger2
@ComponentScan(basePackageClasses = FunctionController.class)
public class ChukonuSpringBootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChukonuSpringBootApplication.class, args);
    }
}
