package com.yxy.chukonu.sso.saml.idp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/saml/**", "/login"))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/saml/metadata", "/saml/sso").permitAll()
                .anyRequest().authenticated()
            )
            // 关键：禁用 Spring Security 自带的 formLogin
            .formLogin(form -> form.disable()) // 👈 这行是重点！
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}