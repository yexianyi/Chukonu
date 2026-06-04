package com.yxy.chukonu.sso.saml.idp;

import org.opensaml.core.config.InitializationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

/**
 * SAML 2.0 IDP 启动类
 * @author yxy
 */
@SpringBootApplication
public class ChukonuSamlIdpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChukonuSamlIdpApplication.class, args);
    }

    /**
     * 初始化 OpenSAML 库（必须）
     */
    @PostConstruct
    public void initOpenSaml() throws Exception {
        InitializationService.initialize();
    }
}