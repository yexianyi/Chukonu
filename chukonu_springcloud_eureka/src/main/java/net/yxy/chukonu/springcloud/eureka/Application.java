package net.yxy.chukonu.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication
//@RestController
public class Application {
    
//    @RequestMapping("/")
//    public String home() {
//        return "Hello world";
//    }

    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}