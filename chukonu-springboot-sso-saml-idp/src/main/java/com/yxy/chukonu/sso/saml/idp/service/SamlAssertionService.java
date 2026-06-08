package com.yxy.chukonu.sso.saml.idp.service;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml.saml2.core.impl.AttributeBuilder;
import org.opensaml.saml.saml2.core.impl.AttributeStatementBuilder;
import org.opensaml.saml.saml2.encryption.Encrypter;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.KeyStoreCredentialResolver;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionConstants;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.impl.SignatureBuilder;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.Signer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.yxy.chukonu.sso.saml.idp.entity.SamlUser;

import net.shibboleth.shared.resolver.CriteriaSet;

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

    public Assertion generateSamlAssertion(SamlUser user, String spEntityId) {
        AssertionBuilder assertionBuilder = (AssertionBuilder) XMLObjectProviderRegistrySupport
                .getBuilderFactory().getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
        Assertion assertion = assertionBuilder.buildObject();

        assertion.setID("_" + UUID.randomUUID());
        assertion.setIssueInstant(Instant.now());
        assertion.setIssuer(buildIssuer());
        assertion.setSubject(buildSubject(user));
        assertion.setConditions(buildConditions(spEntityId));
        
        // 🌟【需求实现】：用户属性映射映射 (nameid在Subject中，此处映射email, displayName)
        assertion.getAttributeStatements().add(buildAttributeStatement(user));

        // 对断言进行 SHA256 + RSA 数字签名
        try {
            Signature signature = buildSignature();
            assertion.setSignature(signature);
            XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(assertion).marshall(assertion);
            Signer.signObject(signature);
        } catch (Exception e) {
            throw new RuntimeException("SAML断言签名失败", e);
        }
        return assertion;
    }

    /**
     * 对明文 Assertion 进行加密 (AES-256-GCM)
     */
    public EncryptedAssertion encryptAssertion(Assertion assertion, Credential spPublicCredential) {
        try {
            // 1. 内容加密参数配置 (AES-256-GCM)
            DataEncryptionParameters dataEncParams = new DataEncryptionParameters();
            dataEncParams.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES256_GCM);

            // 2. 密钥运输加密参数配置 (RSA-OAEP)
            KeyEncryptionParameters keyEncParams = new KeyEncryptionParameters();
            keyEncParams.setEncryptionCredential(spPublicCredential);
            keyEncParams.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSAOAEP);

            // 3. 🌟 核心修复：直接使用两路入参构造函数，并使用内部自带的静态 Placement 属性
            Encrypter encrypter = new Encrypter(dataEncParams, keyEncParams);
            encrypter.setKeyPlacement(Encrypter.KeyPlacement.INLINE); 
            
            return encrypter.encrypt(assertion);
        } catch (Exception e) {
            throw new RuntimeException("SAML断言加密失败，请检查SP凭证合规性", e);
        }
    }

    private Issuer buildIssuer() {
        Issuer issuer = (Issuer) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Issuer.DEFAULT_ELEMENT_NAME).buildObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue(idpEntityId);
        return issuer;
    }

    private Subject buildSubject(SamlUser user) {
        Subject subject = (Subject) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Subject.DEFAULT_ELEMENT_NAME).buildObject(Subject.DEFAULT_ELEMENT_NAME);
        NameID nameID = (NameID) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(NameID.DEFAULT_ELEMENT_NAME).buildObject(NameID.DEFAULT_ELEMENT_NAME);
        nameID.setValue(user.getUsername());
        nameID.setFormat(NameID.UNSPECIFIED);
        subject.setNameID(nameID);
        return subject;
    }

    private AttributeStatement buildAttributeStatement(SamlUser user) {
        AttributeStatementBuilder builder = (AttributeStatementBuilder) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
        AttributeStatement statement = builder.buildObject();

        statement.getAttributes().add(createAttribute("email", user.getEmail()));
        statement.getAttributes().add(createAttribute("displayName", user.getName()));
        return statement;
    }

    private Attribute createAttribute(String name, String value) {
        AttributeBuilder builder = (AttributeBuilder) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
        Attribute attribute = builder.buildObject();
        attribute.setName(name);

        XSStringBuilder stringBuilder = (XSStringBuilder) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(XSString.TYPE_NAME);
        XSString stringValue = stringBuilder.buildObject(Attribute.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        stringValue.setValue(value);

        attribute.getAttributeValues().add(stringValue);
        return attribute;
    }

    private Conditions buildConditions(String spEntityId) {
        Conditions conditions = (Conditions) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Conditions.DEFAULT_ELEMENT_NAME).buildObject(Conditions.DEFAULT_ELEMENT_NAME);
        conditions.setNotBefore(Instant.now());
        // 消息防重放：设置精确失效截断期
        conditions.setNotOnOrAfter(Instant.now().plusSeconds(assertionLifeTime));

        AudienceRestriction audienceRestriction = (AudienceRestriction) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(AudienceRestriction.DEFAULT_ELEMENT_NAME).buildObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);

        Audience audience = (Audience) XMLObjectProviderRegistrySupport.getBuilderFactory()
                .getBuilder(Audience.DEFAULT_ELEMENT_NAME).buildObject(Audience.DEFAULT_ELEMENT_NAME);
        audience.setURI(spEntityId);

        audienceRestriction.getAudiences().add(audience);
        conditions.getAudienceRestrictions().add(audienceRestriction);
        return conditions;
    }

    private Signature buildSignature() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (InputStream is = keystoreResource.getInputStream()) {
            keyStore.load(is, keystorePassword.toCharArray());
        }
        Map<String, String> aliasPwdMap = new HashMap<>();
        aliasPwdMap.put(keyAlias, keyPassword);
        KeyStoreCredentialResolver resolver = new KeyStoreCredentialResolver(keyStore, aliasPwdMap, UsageType.SIGNING);

        CriteriaSet criteriaSet = new CriteriaSet();
        criteriaSet.add(new EntityIdCriterion(keyAlias));
        Credential credential = resolver.resolveSingle(criteriaSet);

        Signature signature = new SignatureBuilder().buildObject();
        signature.setSigningCredential(credential);
        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        return signature;
    }
}