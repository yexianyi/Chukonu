package com.yxy.chukonu.springboot.rabbitmq.service;

import com.yxy.chukonu.springboot.rabbitmq.entity.AlarmMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlarmProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${alarm.exchange}")
    private String exchange;

    @Value("${alarm.routing-key}")
    private String routingKey;

    public AlarmProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAlarm(String level, String content) {
        AlarmMessage msg = new AlarmMessage();
        msg.setLevel(level);
        msg.setContent(content);

        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
        System.out.println("✅ 发送告警：" + content);
    }
}