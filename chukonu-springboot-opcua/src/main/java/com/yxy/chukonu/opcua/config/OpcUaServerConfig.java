package com.yxy.chukonu.opcua.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfigBuilder;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MessageSecurityMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.UserTokenType;
import org.eclipse.milo.opcua.stack.core.types.structured.UserTokenPolicy;
import org.eclipse.milo.opcua.stack.server.EndpointConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yxy.chukonu.opcua.server.SimulationNamespace;

import jakarta.annotation.PreDestroy;

@Configuration
public class OpcUaServerConfig {

    private OpcUaServer server;
    private SimulationNamespace simulationNamespace;

    @Value("${opcua.server.bind-address}")
    private String bindAddress;

    @Value("${opcua.server.port}")
    private int port;

    @Value("${opcua.server.endpoint-path}")
    private String endpointPath;

    @Bean
    public OpcUaServer opcUaServer() throws Exception {
        OpcUaServerConfigBuilder config = new OpcUaServerConfigBuilder();

        // 1. 设置基础信息
        config.setApplicationName(LocalizedText.english("Chukonu SpringBoot Simulation Server"));
        config.setApplicationUri("urn:yxy:chukonu:opcua:server");

        // 3. 根据源码规范：手动创建并装配底层标准的 EndpointConfiguration
        
        EndpointConfiguration endpointConfiguration = EndpointConfiguration.newBuilder()
            .setBindAddress(bindAddress)
            .setBindPort(port)
            .setPath(endpointPath)
            .setSecurityPolicy(SecurityPolicy.None)
            .setSecurityMode(MessageSecurityMode.None)
            .build();

        // 4. 调用源码中覆盖的覆盖方法 setEndpoints 注入端点集合
        Set<EndpointConfiguration> endpoints = new HashSet<>();
        endpoints.add(endpointConfiguration);
        config.setEndpoints(endpoints);

        // 5. 构建服务器实例
        server = new OpcUaServer(config.build());
        
        // 6. 初始化业务命名空间，并注册到服务器的 NamespaceManager 中
        simulationNamespace = new SimulationNamespace(server);
        simulationNamespace.startup(); // 👈 核心：让其加入管理器的生命周期列表
        
        // 7. 启动 Milo 核心服务器基础设施（此时会自动激活上方 simulationNamespace 的 onStartup 钩子）
        server.startup();
        
        return server;
    }

    @PreDestroy
    public void stopServer() {
        if (simulationNamespace != null) {
            simulationNamespace.shutdown();
        }
        if (server != null) {
            server.shutdown();
        }
    }
}