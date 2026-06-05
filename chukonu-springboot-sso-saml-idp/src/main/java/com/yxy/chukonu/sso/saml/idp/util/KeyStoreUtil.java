package com.yxy.chukonu.sso.saml.idp.util;

import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;

public class KeyStoreUtil {

    private static final String KEYSTORE_PATH = "keystore/idp.jks";
    private static final String KEYSTORE_PASS = "123456"; // 替换成你的jks实际密码
    private static final String KEY_ALIAS = "idp";       // 替换成证书别名

    /**
     * 从 JKS 中获取公钥证书的 Base64 字符串（用于元数据展示）
     */
    public static String getPublicKeyFromJks() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new ClassPathResource(KEYSTORE_PATH).getInputStream(), KEYSTORE_PASS.toCharArray());
            Certificate cert = keyStore.getCertificate(KEY_ALIAS);
            if (cert == null) {
                throw new RuntimeException("找不到别名为 [" + KEY_ALIAS + "] 的证书");
            }
            byte[] certBytes = cert.getEncoded();
            return Base64.getEncoder().encodeToString(certBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 🌟【JDK 25 适配版】从 JKS 中加载并“洗白”私钥
     * 将厂商底层的 RSAPrivateCrtKeyImpl 规范化为通用的 PrivateKey 对象，防止 OpenSAML 签名时抛出 InvalidKeyException
     */
    public static PrivateKey getPrivateKeyFromJks() {
        try {
            // 1. 标准加载密钥库
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new ClassPathResource(KEYSTORE_PATH).getInputStream(), KEYSTORE_PASS.toCharArray());
            
            // 2. 提取出带有 sun.security 强封装标签的原始私钥
            PrivateKey rawPrivateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS, KEYSTORE_PASS.toCharArray());
            if (rawPrivateKey == null) {
                throw new RuntimeException("找不到别名为 [" + KEY_ALIAS + "] 的私钥");
            }

            // 3. 核心修复：提取原生私钥的开放标准的 PKCS#8 字节流
            byte[] encodedKey = rawPrivateKey.getEncoded();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            
            // 4. 使用标准的 RSA 密钥工厂重新还原私钥
            // 这样还原出来的私钥对象将完美脱离 java.base 模块的模块化硬隔离，从而通杀任何底层的 XML 签名安全提供者
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            
            return keyFactory.generatePrivate(keySpec);

        } catch (Exception e) {
            throw new RuntimeException("JDK 25 环境下加载或规范化私钥失败", e);
        }
    }
}