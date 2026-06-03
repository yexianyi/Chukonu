//package com.yxy.chukonu.springboot.rabbitmq.config;
//
//import java.util.List;
//
//import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.testcontainers.containers.RabbitMQContainer;
//import org.testcontainers.utility.DockerImageName;
//
//@Configuration
//public class RabbitMqTestContainerConfig {
//
//    
//	@Bean(destroyMethod = "stop")
//	public RabbitMQContainer rabbitMQContainer() {
//	    RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.3.1-management"))
//	            .withAdminUser("admin")
//	            .withAdminPassword("admin123") // 修正：不要混用 withEnv
//	            .withVhost("/");
//	    
//	    container.start();
//	    return container;
//	}
//
//    @Bean
//    public RabbitConnectionDetails rabbitConnectionDetails(RabbitMQContainer container) {
//        return new RabbitConnectionDetails() {
//            public String getHost() { return container.getHost(); }
//            public int getPort() { return container.getAmqpPort(); }
//            @Override public String getUsername() { return "admin"; }
//            @Override public String getPassword() { return "admin123"; }
//            @Override public String getVirtualHost() { return "/"; }
//            @Override public List<Address> getAddresses() {
//                return List.of(new Address(getHost(), getPort()));
//            }
//        };
//    }
//}