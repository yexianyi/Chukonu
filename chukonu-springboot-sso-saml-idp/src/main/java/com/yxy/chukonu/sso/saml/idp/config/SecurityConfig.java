package com.yxy.chukonu.sso.saml.idp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. 禁用 CSRF（针对 SAML 相关的路径，或者直接全局禁用，做 IdP 通常由于接受外部 POST 会禁用）
        	.csrf(csrf -> csrf.disable())
            // 2. 配置路径放行规则
            .authorizeHttpRequests(auth -> auth
                // 放行：GET/POST /login (自定义登录页)，以及 SAML 的两个核心接口
                .requestMatchers("/login", "/saml/metadata", "/saml/sso").permitAll()
                // 其他任何请求都需要经过认证（实际上，未登录的用户访问会被踢到 /login）
                .anyRequest().authenticated()
            )
            
            // 3. 不使用内置的 formLogin，改用自定义的 EntryPoint
            // 未登录的人访问受保护资源时，依然会被 302 重定向到 /idp/login
            // 但点击登录提交时，POST 请求能够毫无阻碍地穿透，直接冲进你的 LoginController 中！
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            );

        return http.build();
    }
}