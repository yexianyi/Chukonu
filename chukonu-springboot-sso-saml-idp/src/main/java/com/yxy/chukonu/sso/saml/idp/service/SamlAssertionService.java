package com.yxy.chukonu.sso.saml.idp.service;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.impl.AssertionBuilder;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.KeyStoreCredentialResolver;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.impl.SignatureBuilder;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.Signer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.yxy.chukonu.sso.saml.idp.entity.SamlUser;

import net.shibboleth.shared.resolver.CriteriaSet;

/**
 * SAML 断言核心服务：生成、签名SAML断言
 */
@Service
public class SamlAssertionService {

    @Value("${saml.idp.entity-id}")
    private String idpEntityId;

    @Value("${saml.idp.assertion-life-time}")
    private Long assertionLifeTime;

    @Value("${saml.idp.keystore.path}")
    private Resource keystoreResource;

    @Value("${saml.idp.keystore.password}")
    private String keystorePassword;

    @Value("${saml.idp.keystore.key-alias}")
    private String keyAlias;

    @Value("${saml.idp.keystore.key-password}")
    private String keyPassword;

    /**
     * 生成SAML断言
     */
    public Assertion generateSamlAssertion(SamlUser user, String spEntityId) {
        // 1. 创建断言构建器
        AssertionBuilder assertionBuilder = (AssertionBuilder) XMLObjectProviderRegistrySupport
                .getBuilderFactory()
                .getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
        Assertion assertion = assertionBuilder.buildObject();

        // 2. 设置断言基础信息
        assertion.setID("_" + UUID.randomUUID());
        assertion.setIssueInstant(Instant.now());
        assertion.setIssuer(buildIssuer());
        assertion.setSubject(buildSubject(user));
        assertion.setConditions(buildConditions(spEntityId));

        // 3. 签名断言
        try {
            Signature signature = buildSignature();
            assertion.setSignature(signature);
            XMLObjectProviderRegistrySupport.getMarshallerFactory()
                    .getMarshaller(assertion)
                    .marshall(assertion);
            Signer.signObject(signature);
        } catch (Exception e) {
            throw new RuntimeException("SAML断言签名失败", e);
        }

        return assertion;
    }

    /**
     * 构建Issuer（IDP标识）
     */
    private Issuer buildIssuer() {
        // 传入当前对象的QName常量
        return (Issuer) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Issuer.DEFAULT_ELEMENT_NAME)
                .buildObject(Issuer.DEFAULT_ELEMENT_NAME);
    }

    /**
     * 构建Subject（用户信息）
     */
    private Subject buildSubject(SamlUser user) {
        Subject subject = (Subject) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Subject.DEFAULT_ELEMENT_NAME)
                .buildObject(Subject.DEFAULT_ELEMENT_NAME);
        NameID nameID = (NameID) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(NameID.DEFAULT_ELEMENT_NAME)
                .buildObject(NameID.DEFAULT_ELEMENT_NAME);
        nameID.setValue(user.getUsername());
        nameID.setFormat(NameID.UNSPECIFIED);
        subject.setNameID(nameID);
        return subject;
    }

    private Conditions buildConditions(String spEntityId) {
        // 1. 构建 Conditions ✅
        Conditions conditions = (Conditions) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Conditions.DEFAULT_ELEMENT_NAME)
                .buildObject(Conditions.DEFAULT_ELEMENT_NAME);

        conditions.setNotBefore(Instant.now());
        conditions.setNotOnOrAfter(Instant.now().plusSeconds(assertionLifeTime));

        // 2. 构建 AudienceRestriction ✅
        AudienceRestriction audienceRestriction = (AudienceRestriction) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(AudienceRestriction.DEFAULT_ELEMENT_NAME)
                .buildObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);

        // 3. 构建 Audience ✅
        Audience audience = (Audience) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Audience.DEFAULT_ELEMENT_NAME)
                .buildObject(Audience.DEFAULT_ELEMENT_NAME);
        audience.setURI(spEntityId);

        // 4. 正确层级组装：Audience -> AudienceRestriction -> Conditions ✅
        audienceRestriction.getAudiences().add(audience);
        conditions.getAudienceRestrictions().add(audienceRestriction);

        return conditions;
    }

    /**
     * 构建断言签名
     */
    private Signature buildSignature() throws Exception {
        // 1、加载JKS密钥库
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (InputStream is = keystoreResource.getInputStream()) {
            keyStore.load(is, keystorePassword.toCharArray());
        }

        // 2、OpenSAML5构造：Map<别名,私钥条目密码>，废弃X500Principal入参
        Map<String, String> aliasPwdMap = new HashMap<>();
        aliasPwdMap.put(keyAlias, keyPassword);
        KeyStoreCredentialResolver resolver = new KeyStoreCredentialResolver(keyStore, aliasPwdMap, UsageType.SIGNING);

        // 3、通过Criteria查询Credential，替代旧版getCredentialByEntityId
        CriteriaSet criteriaSet = new CriteriaSet();
        criteriaSet.add(new EntityIdCriterion(keyAlias));
        Credential credential = resolver.resolveSingle(criteriaSet);
        if (credential == null) {
            throw new RuntimeException("根据别名[" + keyAlias + "]未查询到签名私钥凭证");
        }

        // 4、构建签名对象（原有逻辑保留）
        Signature signature = new SignatureBuilder().buildObject();
        signature.setSigningCredential(credential);
        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

        return signature;
    }
}