package com.yxy.chukonu.springboot.rabbitmq.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AlarmMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String level;
    private String content;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}