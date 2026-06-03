package com.yxy.chukonu.springboot.rabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;
import com.yxy.chukonu.springboot.rabbitmq.service.AlarmProducer;
import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final AlarmProducer alarmProducer;

    public Application(AlarmProducer alarmProducer) {
        this.alarmProducer = alarmProducer;
    }

    // 1. 管理单例静态容器
    private static final RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.3.1-management"))
            .withAdminUser("admin")
            .withAdminPassword("admin123");

    public static void main(String[] args) {
        // 2. 启动容器获取随机映射端口
        container.start();

        // 3. 使用 SpringApplication 显式挂载初始化器
        SpringApplication app = new SpringApplication(Application.class);
        app.addInitializers(new RabbitMQPropertiesInitializer());
        app.run(args);
    }

    /**
     * 核心：在 Spring Boot 环境准备的最早期，将动态连接参数打入配置源中
     */
    static class RabbitMQPropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            System.setProperty("spring.amqp.deserialization.trust.all", "true");

            Map<String, Object> tcProps = Map.of(
                    "spring.rabbitmq.host", container.getHost(),
                    "spring.rabbitmq.port", container.getAmqpPort(), // 这里直接是 Integer，Spring 内部会自动转换
                    "spring.rabbitmq.username", "admin",
                    "spring.rabbitmq.password", "admin123",
                    "spring.rabbitmq.virtual-host", "/"
            );

            MapPropertySource propertySource = new MapPropertySource("testcontainers-properties", tcProps);
            // 确保高优先级插入，防止被默认的 guest/5672 覆盖
            applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
        }
    }

    @Bean(destroyMethod = "stop")
    public RabbitMQContainer rabbitMQContainer() {
        return container;
    }

    @Override
    public void run(String... args) throws Exception {
    	System.out.println("\n🚀 开始通过 [TLS/SSL 安全通道] 发送测试告警数据...");
        for (int i = 1; i <= 5; i++) {
            alarmProducer.sendAlarm("ERROR", "🔒 [安全通道] 核心生产设备温度过高告警测试 - 编号 " + i);
            Thread.sleep(1000);
        }
    }
}