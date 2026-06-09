package com.yxy.chukonu.sso.saml.sp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SamlSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. 细化路径放行与保护规则
            .authorizeHttpRequests(authorize -> authorize
                // 仅放行匿名首页、错误页、元数据端点
                .requestMatchers("/", "/error", "/saml/metadata").permitAll()
                .requestMatchers("/sp/user").authenticated()
                .anyRequest().authenticated()
            )
            
            // 2. 绑定登录成功后的默认跳转绝对路径（框架会自动帮它套上 /sp 上下文）
            .saml2Login(saml2 -> saml2
                .loginProcessingUrl("/saml/acs") 
                // 🌟 保持 "/user"，去掉之前的 contextPath 拼接
                .defaultSuccessUrl("/user", true)
            )
            
            // 3. 常规本地注销保持不变
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
            )
            
            // 4. 标准 SAML2 SLO 联动保持不变
            .saml2Logout(saml2Logout -> saml2Logout
                .logoutRequest(request -> request.logoutUrl("/saml/slo"))
                .logoutResponse(response -> response.logoutUrl("/saml/slo"))
            );

        return http.build();
    }
}