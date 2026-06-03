package com.yxy.chukonu.springboot.rabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.yxy.chukonu.springboot.rabbitmq.config.RabbitMQTestcontainerInitializer;
import com.yxy.chukonu.springboot.rabbitmq.service.AlarmProducer;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final AlarmProducer alarmProducer;

    public Application(AlarmProducer alarmProducer) {
        this.alarmProducer = alarmProducer;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        // 显式挂载独立出来的初始化器
        app.addInitializers(new RabbitMQTestcontainerInitializer());
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n🚀 开始发送测试告警数据...");
        for (int i = 1; i <= 5; i++) {
            alarmProducer.sendAlarm("ERROR", "🔒 核心生产设备温度过高告警测试 - 编号 " + i);
            Thread.sleep(1000);
        }
    }
}