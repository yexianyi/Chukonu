package com.yxy.chukonu.springboot.rabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import com.yxy.chukonu.springboot.rabbitmq.service.AlarmProducer;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final AlarmProducer alarmProducer;

    public Application(AlarmProducer alarmProducer) {
        this.alarmProducer = alarmProducer;
    }

    private static final RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.3.1-management"))
            .withAdminUser("admin")
            .withAdminPassword("admin123");

    public static void main(String[] args) {
        // 1. 在 Spring 启动前先启动容器，以便获取它的动态端口
        container.start();

        // 2. 将容器的连接信息设置为系统属性，让 Spring Boot 能够读取到
        System.setProperty("spring.amqp.deserialization.trust.all", "true");
        System.setProperty("spring.rabbitmq.host", container.getHost());
        System.setProperty("spring.rabbitmq.port", String.valueOf(container.getAmqpPort()));
        System.setProperty("spring.rabbitmq.username", "admin");
        System.setProperty("spring.rabbitmq.password", "admin123");
        System.setProperty("spring.rabbitmq.virtual-host", "/");

        SpringApplication.run(Application.class, args);
    }

    @Bean(destroyMethod = "stop")
    public RabbitMQContainer rabbitMQContainer() {
        // 直接返回已经启动的单例容器
        return container;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 开始发送测试告警...");
        for (int i = 1; i <= 5; i++) {
            alarmProducer.sendAlarm("ERROR", "CPU 使用率过高 告警" + i);
            Thread.sleep(1000);
        }
    }
}