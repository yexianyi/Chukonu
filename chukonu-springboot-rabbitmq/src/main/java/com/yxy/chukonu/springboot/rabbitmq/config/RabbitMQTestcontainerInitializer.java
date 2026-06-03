package com.yxy.chukonu.springboot.rabbitmq.config;

import java.util.Map;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * 独立的 RabbitMQ Testcontainers 初始化器
 * 负责容器的生命周期拉起以及向 Spring 容器动态注入连接属性
 */
public class RabbitMQTestcontainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    // 将容器内聚在初始化器内部管理
    private static final RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.3.1-management"))
            .withAdminUser("admin")
            .withAdminPassword("admin123");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 1. 确保在 Spring 读取环境配置前，容器已经完全启动
        if (!container.isRunning()) {
            System.out.println("⏳ 正在独立初始化器中启动 RabbitMQ 容器...");
            container.start();
        }

        // 2. 注入全局反序列化信任策略
        System.setProperty("spring.amqp.deserialization.trust.all", "true");

        // 3. 动态提取容器映射信息
        Map<String, Object> tcProps = Map.of(
                "spring.rabbitmq.host", container.getHost(),
                "spring.rabbitmq.port", container.getAmqpPort(),
                "spring.rabbitmq.username", "admin",
                "spring.rabbitmq.password", "admin123",
                "spring.rabbitmq.virtual-host", "/"
        );

        MapPropertySource propertySource = new MapPropertySource("testcontainers-properties", tcProps);
        // 4. 强行插入至 Spring 属性源首位，覆盖本地默认配置
        applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
        System.out.println("✅ Testcontainers 动态连接参数已成功注入 Spring 环境！");
        
        // 5. 将容器实例注册为 Bean，以便在需要时（如销毁或监控）让 Spring 托管
        applicationContext.getBeanFactory().registerSingleton("rabbitMQContainer", container);
    }
}