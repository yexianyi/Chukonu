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
            // 1. 放行静态根路径、登录引导页与错误页
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/error").permitAll()
                .anyRequest().authenticated()
            )
            
            // 配合 YML 自定义 ACS 地址
            // 拦截并解析发送到 /saml/acs 的 SAMLResponse 登录响应报文
            .saml2Login(saml2 -> saml2
                .loginProcessingUrl("/saml/acs") 
                .defaultSuccessUrl("/user", true)
            )
            
            // 3. 开启常规的本地注销：当访问 /logout 时，清理 SP 本地会话
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
            )
            
            // 4.开启标准 SAML2 SLO 联动
            // 将过滤器的默认端点覆盖为你在 YML 中自定义的拦截路径
            .saml2Logout(saml2Logout -> saml2Logout
                // 接收并处理来自 IDP 主动发起的登出请求 (LogoutRequest) 拦截路径
                .logoutRequest(request -> request.logoutUrl("/saml/slo"))
                // 接收并处理由 SP 发起登出后，IDP 返回的登出成功回执 (LogoutResponse) 拦截路径
                .logoutResponse(response -> response.logoutUrl("/saml/slo/response"))
            );

        return http.build();
    }
}