package com.yxy.chukonu.springboot.rabbitmq.config;


import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

/**
 * Windows + Rancher Desktop 自动环境变量配置
 * 启动时自动设置，无需手动配置系统环境变量
 */
@Component
public class TestcontainersRancherEnvSetup implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication app) {
        System.out.println("Windows 环境：自动配置 Testcontainers + Rancher Desktop");

        Map<String, Object> envMap = new HashMap<>();

        // ==================== WINDOWS 专用 ====================
        envMap.put("DOCKER_HOST", "npipe:////./pipe/dockerDesktopLinuxEngine");
        envMap.put("TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE", "/var/run/docker.sock");
        envMap.put("TESTCONTAINERS_HOST_OVERRIDE", "127.0.0.1");
        
        // Windows必须关闭Ryuk，否则报错
        envMap.put("TESTCONTAINERS_RYUK_DISABLED", "true");

        // 加载到Spring环境
        env.getPropertySources().addFirst(new MapPropertySource("testcontainers-rancher", envMap));
    }
}