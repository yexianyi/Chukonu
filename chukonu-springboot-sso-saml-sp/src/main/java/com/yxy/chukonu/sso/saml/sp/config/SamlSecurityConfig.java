package com.yxy.chukonu.sso.saml.sp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SamlSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 允许任何人访问根路径
                .requestMatchers("/").permitAll()
                // 其他任何请求都必须经过 SAML 认证
                .anyRequest().authenticated()
            )
            // 开启标准 SAML2 登录流程
            .saml2Login(Customizer.withDefaults())
            // 开启标准 SAML2 登出流程
            .saml2Logout(Customizer.withDefaults());

        return http.build();
    }
}