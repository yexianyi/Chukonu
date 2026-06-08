package com.yxy.chukonu.sso.saml.idp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "saml.idp")
public class SamlProperties {

    private String entityId;
    private Long assertionLifeTime;
    private List<TrustedSp> trustedSps = new ArrayList<>();

    @Data
    public static class TrustedSp {
        private String entityId;
        private String acsUrl;
        private String sloUrl;
        private String encryptionCertPath;

    }

}