package com.yxy.chukonu.sso.saml.idp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "saml.idp")
public class SamlProperties {
    private String entityId;
    private String ssoUrl;
    private String sloUrl; // 🌟 对应应用 yml 中新增的 slo-url 属性
    private String metadataUrl;
    private KeystoreProperties keystore;
    private long assertionLifeTime;
    private List<TrustedSp> trustedSps;

    @Data
    public static class KeystoreProperties {
        private String path;
        private String password;
        private String keyAlias;
        private String keyPassword;
    }

    @Data
    public static class TrustedSp {
        private String entityId;
        private String acsUrl;
        private String sloUrl;
        private String encryptionCertPath;
    }
}