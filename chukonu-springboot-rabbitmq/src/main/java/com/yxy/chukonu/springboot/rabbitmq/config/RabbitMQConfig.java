package com.yxy.chukonu.springboot.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${alarm.exchange}")
    private String exchange;

    @Value("${alarm.queue}")
    private String queue;

    @Value("${alarm.routing-key}")
    private String routingKey;

    @Bean
    public DirectExchange alarmExchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Queue alarmQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Binding alarmBinding() {
        return BindingBuilder.bind(alarmQueue())
                .to(alarmExchange())
                .with(routingKey);
    }
    
}