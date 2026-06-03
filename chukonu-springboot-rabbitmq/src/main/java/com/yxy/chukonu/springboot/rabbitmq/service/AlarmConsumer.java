package com.yxy.chukonu.springboot.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.yxy.chukonu.springboot.rabbitmq.entity.AlarmMessage;

@Component
public class AlarmConsumer {

    @RabbitListener(queues = "${alarm.queue}")
    public void receiveAlarm(AlarmMessage alarm,
                            Channel channel,
                            @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {

        System.out.println("\n=====================================");
        System.out.println("🔔 收到告警：" + alarm.getContent());
        System.out.println("级别：" + alarm.getLevel());
        System.out.println("时间：" + alarm.getTimestamp());
        System.out.println("=====================================\n");

        channel.basicAck(tag, false);
    }
}