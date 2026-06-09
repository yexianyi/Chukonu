package com.yxy.chukonu.sso.saml.idp.service;

import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.yxy.chukonu.sso.saml.idp.config.SamlProperties;

import jakarta.annotation.PostConstruct;

@Service
public class SpTrustService {

    private final SamlProperties samlProperties;
    private final ResourceLoader resourceLoader;
    private final Map<String, SamlProperties.TrustedSp> spCache = new HashMap<>();
    private final Map<String, Credential> spCredentialCache = new HashMap<>();

    public SpTrustService(SamlProperties samlProperties, ResourceLoader resourceLoader) {
        this.samlProperties = samlProperties;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() {
        // 从 yml 加载的配置灌入缓存
        for (SamlProperties.TrustedSp sp : samlProperties.getTrustedSps()) {
            spCache.put(sp.getEntityId(), sp);
            
            // 如果配置了 SP 的公钥证书，提前解析为 OpenSAML 的 Credential，用于 Assertion 加密
            if (sp.getEncryptionCertPath() != null && !sp.getEncryptionCertPath().isBlank()) {
                try (InputStream is = resourceLoader.getResource(sp.getEncryptionCertPath()).getInputStream()) {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
                    BasicX509Credential credential = new BasicX509Credential(cert);
                    spCredentialCache.put(sp.getEntityId(), credential);
                } catch (Exception e) {
                    System.err.println("加载 SP [" + sp.getEntityId() + "] 的加密证书失败: " + e.getMessage());
                }
            }
        }
    }

    public boolean isTrustedSp(String entityId, String acsUrl) {
    	// 暂时仅对entityId做校验，实际可以对acsUrl等一系列参数做校验
        SamlProperties.TrustedSp sp = spCache.get(entityId);
        if (sp == null) return false;
        return sp.getEntityId().equalsIgnoreCase(entityId);
    }

    public String getSloUrlByEntityId(String entityId) {
        SamlProperties.TrustedSp sp = spCache.get(entityId);
        return sp != null ? sp.getSloUrl() : null;
    }

    public Credential getSpEncryptionCredential(String entityId) {
        return spCredentialCache.get(entityId);
    }
}