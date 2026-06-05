package com.yxy.chukonu.sso.saml.idp.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.impl.ResponseBuilder;
import org.opensaml.saml.saml2.core.impl.StatusBuilder;
import org.opensaml.saml.saml2.core.impl.StatusCodeBuilder;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml.saml2.metadata.impl.EntityDescriptorBuilder;
import org.opensaml.saml.saml2.metadata.impl.IDPSSODescriptorBuilder;
import org.opensaml.saml.saml2.metadata.impl.SingleSignOnServiceBuilder;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.impl.SignatureBuilder;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.Signer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yxy.chukonu.sso.saml.idp.entity.SamlUser;
import com.yxy.chukonu.sso.saml.idp.service.SamlAssertionService;
import com.yxy.chukonu.sso.saml.idp.util.KeyStoreUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/saml")
public class SamlIdpController {

    @Value("${saml.idp.entity-id}")
    private String idpEntityId;

    @Value("${saml.idp.sso-url}")
    private String ssoUrl;

    private final String publicKey = KeyStoreUtil.getPublicKeyFromJks();
    
    private final SamlAssertionService samlAssertionService;

    public SamlIdpController(SamlAssertionService samlAssertionService) {
        this.samlAssertionService = samlAssertionService;
    }

    /**
     * 元数据接口
     */
    @GetMapping("/metadata")
    public void metadata(HttpServletResponse response) throws Exception {
        response.setContentType("application/xml;charset=UTF-8");

        EntityDescriptor entityDescriptor = new EntityDescriptorBuilder()
                .buildObject(EntityDescriptor.DEFAULT_ELEMENT_NAME);
        entityDescriptor.setEntityID(idpEntityId);

        IDPSSODescriptor idpSsoDescriptor = new IDPSSODescriptorBuilder()
                .buildObject(IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        idpSsoDescriptor.setWantAuthnRequestsSigned(false);
        idpSsoDescriptor.addSupportedProtocol("urn:oasis:names:tc:SAML:2.0:protocol");

        SingleSignOnService ssoService = new SingleSignOnServiceBuilder()
                .buildObject(SingleSignOnService.DEFAULT_ELEMENT_NAME);
        ssoService.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect");
        ssoService.setLocation(ssoUrl);

        idpSsoDescriptor.getSingleSignOnServices().add(ssoService);
        
        XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
        KeyDescriptor keyDescriptor = (KeyDescriptor) builderFactory
                .getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME)
                .buildObject(KeyDescriptor.DEFAULT_ELEMENT_NAME);
        keyDescriptor.setUse(UsageType.SIGNING);

        KeyInfo keyInfo = (KeyInfo) builderFactory
                .getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME)
                .buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);

        X509Data x509Data = (X509Data) builderFactory
                .getBuilder(X509Data.DEFAULT_ELEMENT_NAME)
                .buildObject(X509Data.DEFAULT_ELEMENT_NAME);

        X509Certificate cert = (X509Certificate) builderFactory
                .getBuilder(X509Certificate.DEFAULT_ELEMENT_NAME)
                .buildObject(X509Certificate.DEFAULT_ELEMENT_NAME);
        cert.setValue(publicKey);

        x509Data.getX509Certificates().add(cert);
        keyInfo.getX509Datas().add(x509Data);
        keyDescriptor.setKeyInfo(keyInfo);

        idpSsoDescriptor.getKeyDescriptors().add(keyDescriptor);
        entityDescriptor.getRoleDescriptors().add(idpSsoDescriptor);

        try (PrintWriter writer = response.getWriter()) {
            marshallToWriter(entityDescriptor, writer);
        }
    }

    /**
     * 核心单点登录处理方法 (全量修复版)
     */
    @GetMapping("/sso")
    public void processSso(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        // 1. 校验 IDP 本地登录态 (从 Session 中获取你在 LoginController 里存入的 samlUser)
        SamlUser user = (SamlUser) session.getAttribute("samlUser");
        if (user == null) {
            String requestSaml = request.getParameter("SAMLRequest");
            String requestRelay = request.getParameter("RelayState");
            
            if (requestSaml != null && !requestSaml.isBlank()) {
                session.setAttribute("SAMLRequest_cache", requestSaml);
            }
            if (requestRelay != null && !requestRelay.isBlank()) {
                session.setAttribute("RelayState_cache", requestRelay);
            }
            
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. 提取 SAML 核心请求参数
        String samlRequest = request.getParameter("SAMLRequest");
        if (samlRequest == null || samlRequest.isBlank()) {
            samlRequest = (String) session.getAttribute("SAMLRequest_cache");
        }
        
        String relayState = request.getParameter("RelayState");
        if (relayState == null || relayState.isBlank()) {
            relayState = (String) session.getAttribute("RelayState_cache");
        }
        
        session.removeAttribute("SAMLRequest_cache");
        session.removeAttribute("RelayState_cache");

        if (samlRequest == null || samlRequest.isBlank()) {
            throw new RuntimeException("缺少SAMLRequest参数，非法SP请求或会话已超时");
        }

        // 3. 解析 SAMLRequest 
        AuthnRequest authnRequest = parseSamlRequest(samlRequest);
        String spEntityId = authnRequest.getIssuer().getValue();
        String spAcsUrl = authnRequest.getAssertionConsumerServiceURL();
        if (spAcsUrl == null || spAcsUrl.isBlank()) {
            throw new RuntimeException("SP发来的SAMLRequest中未包含AssertionConsumerServiceURL");
        }

        // 4. 调用服务：生成内部带签名的 Assertion
        // ⚠️注意：哪怕这里面的 assertion 签名使用了旧私钥导致底层带了 sun.security.rsa 标签，我们也在下一步强行洗白它
        Assertion assertion = samlAssertionService.generateSamlAssertion(user, spEntityId);
        
        if (assertion.getIssuer() == null || assertion.getIssuer().getValue() == null || assertion.getIssuer().getValue().isBlank()) {
            XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
            Issuer assertionIssuer = (Issuer) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME)
                    .buildObject(Issuer.DEFAULT_ELEMENT_NAME);
            // 强行赋予 IDP 的 Entity ID（对应你的 application.properties 中的 saml.idp.entity-id）
            assertionIssuer.setValue(idpEntityId); 
            assertion.setIssuer(assertionIssuer);
        }

     // 5. 封装最外层 SAMLResponse 容器
        Response samlResponseObj = buildSamlResponse(authnRequest.getID(), spAcsUrl, assertion);

        // 🌟【JDK 25 强效物理拦截机制 - 双重加签洗白版】
        if (assertion.getSignature() != null && assertion.getSignature().getSigningCredential() != null) {
            org.opensaml.security.credential.Credential originalCred = assertion.getSignature().getSigningCredential();
            
            // ① 从你的工具类中抓出 100% 纯净标准的标准私钥对象
            java.security.PrivateKey cleanPrivKey = com.yxy.chukonu.sso.saml.idp.util.KeyStoreUtil.getPrivateKeyFromJks();
            
            // ② 物理组装一个绝对合规的全新通用 OpenSAML 凭证容器
            org.opensaml.security.credential.BasicCredential cleanCredential = new org.opensaml.security.credential.BasicCredential();
            cleanCredential.setPublicKey(originalCred.getPublicKey()); 
            cleanCredential.setPrivateKey(cleanPrivKey);              
            
            // ==================== 1. 先对内部的 Assertion 进行强制洗白与独立加签 ====================
            assertion.getSignature().setSigningCredential(cleanCredential);
            // 确保断言的算法也规范化
            assertion.getSignature().setSignatureAlgorithm(org.opensaml.xmlsec.signature.support.SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
            assertion.getSignature().setCanonicalizationAlgorithm(org.opensaml.xmlsec.signature.support.SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
            
            // 显式序列化并签署内部 Assertion
            Marshaller assertionMarshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(assertion);
            assertionMarshaller.marshall(assertion);
            Signer.signObject(assertion.getSignature()); // 🔥 这一步能确保 Assertion 的签名长度不再为 0！

            // ==================== 2. 再为最外层的 Response 容器挂载并签署数字签名 ====================
            Signature responseSignature = new org.opensaml.xmlsec.signature.impl.SignatureBuilder().buildObject();
            responseSignature.setSigningCredential(cleanCredential); 
            responseSignature.setSignatureAlgorithm(org.opensaml.xmlsec.signature.support.SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
            responseSignature.setCanonicalizationAlgorithm(org.opensaml.xmlsec.signature.support.SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
            samlResponseObj.setSignature(responseSignature);
            
            // 显式序列化并签署外层 Response
            Marshaller responseMarshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(samlResponseObj);
            responseMarshaller.marshall(samlResponseObj);
            Signer.signObject(responseSignature);
        }

        // 6. 将双重纯净签名的 SAMLResponse 序列化并转换为用于 POST 的 Base64 字符串
        String samlResponseBase64 = marshallAndBase64Encode(samlResponseObj);

        // 7. 渲染并吐出前端 HTML 自动提交表单
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><title>SAML 2.0 IdP POST Binding</title></head>");
        out.println("<body onload='document.forms[0].submit()'>");
        out.println("<form action='" + spAcsUrl + "' method='post'>");
        out.println("<input type='hidden' name='SAMLResponse' value='" + samlResponseBase64 + "'>");
        if (relayState != null && !relayState.isBlank()) {
            out.println("<input type='hidden' name='RelayState' value='" + relayState + "'>");
        }
        out.println("</form></body></html>");
        out.flush();
    }

    /**
     * 解析 HTTP-Redirect 绑定的 SAMLRequest
     */
    private AuthnRequest parseSamlRequest(String samlRequest) throws Exception {
        String decodedStr = URLDecoder.decode(samlRequest, StandardCharsets.UTF_8);
        byte[] decodedBytes;
        try {
            decodedBytes = Base64.getDecoder().decode(decodedStr);
        } catch (IllegalArgumentException e) {
            decodedBytes = Base64.getDecoder().decode(samlRequest);
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
             InflaterInputStream iis = new InflaterInputStream(bais, new Inflater(true));
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            byte[] buffer = new byte[1024];
            int len;
            while ((len = iis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] xmlBytes = baos.toByteArray();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xmlBytes));
            Element element = doc.getDocumentElement();

            Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(element);
            if (unmarshaller == null) {
                throw new RuntimeException("未能为该元素找到对应的 OpenSAML Unmarshaller: " + element.getLocalName());
            }
            return (AuthnRequest) unmarshaller.unmarshall(element);
        }
    }

    /**
     * 构建规范的 SAML Response 容器
     */
    private Response buildSamlResponse(String inResponseToId, String acsUrl, Assertion assertion) {
        XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();

        ResponseBuilder responseBuilder = (ResponseBuilder) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);
        Response response = responseBuilder.buildObject();
        response.setID("_" + UUID.randomUUID().toString());
        response.setIssueInstant(Instant.now());
        response.setDestination(acsUrl);
        response.setInResponseTo(inResponseToId); 

        Issuer issuer = (Issuer) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME).buildObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue(idpEntityId);
        response.setIssuer(issuer);

        StatusBuilder statusBuilder = (StatusBuilder) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
        Status status = statusBuilder.buildObject();
        StatusCodeBuilder statusCodeBuilder = (StatusCodeBuilder) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
        StatusCode statusCode = statusCodeBuilder.buildObject();
        statusCode.setValue(StatusCode.SUCCESS);
        status.setStatusCode(statusCode);
        response.setStatus(status);

        response.getAssertions().add(assertion);

        return response;
    }

    /**
     * 将 XMLObject 转化为 XML 字符串后，进行 Base64 编码
     */
    private String marshallAndBase64Encode(XMLObject xmlObject) throws Exception {
        // 如果前面签名阶段已经 Marshall 过了，直接拿到当时的 DOM 节点，防止重复初始化
        Element element = xmlObject.getDOM();
        if (element == null) {
            Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
            element = marshaller.marshall(xmlObject);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        transformer.transform(new DOMSource(element), new StreamResult(baos));
        byte[] xmlBytes = baos.toByteArray();
        
        return Base64.getEncoder().encodeToString(xmlBytes);
    }

    private void marshallToWriter(XMLObject xmlObject, PrintWriter writer) throws Exception {
        Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
        Element element = marshaller.marshall(xmlObject);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(element), new StreamResult(writer));
    }
}