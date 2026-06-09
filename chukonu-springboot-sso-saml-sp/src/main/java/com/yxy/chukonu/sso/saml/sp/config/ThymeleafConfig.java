package com.yxy.chukonu.sso.saml.sp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
public class ThymeleafConfig {

    // 让 Thymeleaf 可以拿到 #authentication 对象
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    // 启用 request/session 上下文（让 Thymeleaf 能识别请求范围的对象）
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}