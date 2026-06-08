package com.yxy.chukonu.sso.saml.idp.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.LogoutResponse;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.impl.LogoutResponseBuilder;
import org.opensaml.saml.saml2.core.impl.ResponseBuilder;
import org.opensaml.saml.saml2.core.impl.StatusBuilder;
import org.opensaml.saml.saml2.core.impl.StatusCodeBuilder;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml.saml2.metadata.impl.EntityDescriptorBuilder;
import org.opensaml.saml.saml2.metadata.impl.IDPSSODescriptorBuilder;
import org.opensaml.saml.saml2.metadata.impl.SingleSignOnServiceBuilder;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.Signer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yxy.chukonu.sso.saml.idp.entity.SamlUser;
import com.yxy.chukonu.sso.saml.idp.service.SamlAssertionService;
import com.yxy.chukonu.sso.saml.idp.service.SpTrustService;
import com.yxy.chukonu.sso.saml.idp.util.KeyStoreUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/saml")
public class SamlIdpController {

    @Value("${saml.idp.entity-id}")
    private String idpEntityId;

    @Value("${saml.idp.sso-url}")
    private String ssoUrl;

    private final String publicKey;

    private final SamlAssertionService samlAssertionService;
    private final SpTrustService spTrustService;

    public SamlIdpController(SamlAssertionService samlAssertionService, SpTrustService spTrustService) {
        this.samlAssertionService = samlAssertionService;
        this.spTrustService = spTrustService;
        this.publicKey = KeyStoreUtil.getPublicKeyFromJks(); 
    }

    /**
     * SSO 登录接收端点
     */
    @GetMapping("/sso")
    public void processSso(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        SamlUser user = (SamlUser) session.getAttribute("samlUser");
        if (user == null) {
            String requestSaml = request.getParameter("SAMLRequest");
            String requestRelay = request.getParameter("RelayState");
            if (requestSaml != null && !requestSaml.isBlank()) session.setAttribute("SAMLRequest_cache", requestSaml);
            if (requestRelay != null && !requestRelay.isBlank()) session.setAttribute("RelayState_cache", requestRelay);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String samlRequest = request.getParameter("SAMLRequest");
        if (samlRequest == null || samlRequest.isBlank()) samlRequest = (String) session.getAttribute("SAMLRequest_cache");
        String relayState = request.getParameter("RelayState");
        if (relayState == null || relayState.isBlank()) relayState = (String) session.getAttribute("RelayState_cache");
        
        session.removeAttribute("SAMLRequest_cache");
        session.removeAttribute("RelayState_cache");

        if (samlRequest == null || samlRequest.isBlank()) {
            throw new RuntimeException("缺少SAMLRequest参数");
        }

        // 1. 解析请求
        AuthnRequest authnRequest = parseSamlRequest(samlRequest);
        String spEntityId = authnRequest.getIssuer().getValue();
        String spAcsUrl = authnRequest.getAssertionConsumerServiceURL();

        // 2. 用从 yml 加载的白名单进行严格校验
        if (!spTrustService.isTrustedSp(spEntityId, spAcsUrl)) {
            throw new SecurityException("安全拦截：该 SP 发起的请求或其 ACS 注册地址未记录在白名单内！");
        }

        // 3. 构建核心断言
        Assertion assertion = samlAssertionService.generateSamlAssertion(user, spEntityId);
        
        // 4. 初始化 Response 容器
        Response samlResponseObj = buildSamlResponseContainer(authnRequest.getID(), spAcsUrl);
        
        // 根据 yml 配置自动决策：配置了公钥证书就启用 AES-256-GCM 加密，否则直接放明文签名断言
        Credential spPublicCred = spTrustService.getSpEncryptionCredential(spEntityId);
        if (spPublicCred != null) {
            EncryptedAssertion encAssertion = samlAssertionService.encryptAssertion(assertion, spPublicCred);
            samlResponseObj.getEncryptedAssertions().add(encAssertion);
        } else {
            samlResponseObj.getAssertions().add(assertion);
        }

        // 5. 整体进行数字签名（SHA256 + RSA）- JDK 25 兼容版规范清洗加签
        java.security.PrivateKey cleanPrivKey = KeyStoreUtil.getPrivateKeyFromJks();
        org.opensaml.security.credential.BasicCredential cleanCredential = new org.opensaml.security.credential.BasicCredential();
        cleanCredential.setPrivateKey(cleanPrivKey);              
        
        Signature responseSignature = new org.opensaml.xmlsec.signature.impl.SignatureBuilder().buildObject();
        responseSignature.setSigningCredential(cleanCredential); 
        responseSignature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
        responseSignature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        samlResponseObj.setSignature(responseSignature);
        
        Marshaller responseMarshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(samlResponseObj);
        responseMarshaller.marshall(samlResponseObj);
        Signer.signObject(responseSignature);

        // 6. 支持 HTTP-POST Binding 发送回给 SP
        String samlResponseBase64 = marshallAndBase64Encode(samlResponseObj);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><body onload='document.forms[0].submit()'>");
        out.println("<form action='" + spAcsUrl + "' method='post'>");
        out.println("<input type='hidden' name='SAMLResponse' value='" + samlResponseBase64 + "'>");
        if (relayState != null && !relayState.isBlank()) {
            out.println("<input type='hidden' name='RelayState' value='" + relayState + "'>");
        }
        out.println("</form></body></html>");
        out.flush();
    }

    /**
     * SLO 单点登出端点
     */
    @RequestMapping("/slo")
    public void processSlo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String samlRequest = request.getParameter("SAMLRequest");

        // 1. 销毁本地 IDP 会话
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 2. 如果是外部 SP 规范发起的登出通知
        if (samlRequest != null && !samlRequest.isBlank()) {
            XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
            LogoutResponseBuilder responseBuilder = (LogoutResponseBuilder) builderFactory.getBuilder(LogoutResponse.DEFAULT_ELEMENT_NAME);
            LogoutResponse logoutResponse = responseBuilder.buildObject();
            logoutResponse.setID("_" + UUID.randomUUID().toString());
            logoutResponse.setIssueInstant(Instant.now());
            
            StatusBuilder statusBuilder = (StatusBuilder) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
            Status status = statusBuilder.buildObject();
            StatusCodeBuilder statusCodeBuilder = (StatusCodeBuilder) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
            StatusCode statusCode = statusCodeBuilder.buildObject();
            statusCode.setValue(StatusCode.SUCCESS);
            status.setStatusCode(statusCode);
            logoutResponse.setStatus(status);

            Issuer issuer = (Issuer) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME).buildObject(Issuer.DEFAULT_ELEMENT_NAME);
            issuer.setValue(idpEntityId);
            logoutResponse.setIssuer(issuer);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h3>单点登出成功！已为您安全广播并清理全部关联会话。</h3>");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h3>本地退出成功。</h3>");
        }
    }

    /**
     * 🌟【需求实现】：发布全套完整的元数据配置文档（包含SLO与双绑定）
     */
    @GetMapping("/metadata")
    public void metadata(HttpServletResponse response) throws Exception {
        response.setContentType("application/xml;charset=UTF-8");

        EntityDescriptor entityDescriptor = new EntityDescriptorBuilder().buildObject(EntityDescriptor.DEFAULT_ELEMENT_NAME);
        entityDescriptor.setEntityID(idpEntityId);

        IDPSSODescriptor idpSsoDescriptor = new IDPSSODescriptorBuilder().buildObject(IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        idpSsoDescriptor.setWantAuthnRequestsSigned(false);
        idpSsoDescriptor.addSupportedProtocol("urn:oasis:names:tc:SAML:2.0:protocol");

        // 双绑定声明：1. HTTP-Redirect
        SingleSignOnService ssoRedirectService = new SingleSignOnServiceBuilder().buildObject(SingleSignOnService.DEFAULT_ELEMENT_NAME);
        ssoRedirectService.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect");
        ssoRedirectService.setLocation(ssoUrl);
        idpSsoDescriptor.getSingleSignOnServices().add(ssoRedirectService);

        // 双绑定声明：2. HTTP-POST
        SingleSignOnService ssoPostService = new SingleSignOnServiceBuilder().buildObject(SingleSignOnService.DEFAULT_ELEMENT_NAME);
        ssoPostService.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        ssoPostService.setLocation(ssoUrl);
        idpSsoDescriptor.getSingleSignOnServices().add(ssoPostService);

        // SLO 登出端点元数据加入
        SingleLogoutService sloService = new org.opensaml.saml.saml2.metadata.impl.SingleLogoutServiceBuilder().buildObject();
        sloService.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect");
        sloService.setLocation(ssoUrl.replace("/sso", "/slo")); 
        idpSsoDescriptor.getSingleLogoutServices().add(sloService);
        
        // 公钥证书声明
        XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
        KeyDescriptor keyDescriptor = (KeyDescriptor) builderFactory.getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME).buildObject(KeyDescriptor.DEFAULT_ELEMENT_NAME);
        keyDescriptor.setUse(UsageType.SIGNING);

        KeyInfo keyInfo = (KeyInfo) builderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME).buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
        X509Data x509Data = (X509Data) builderFactory.getBuilder(X509Data.DEFAULT_ELEMENT_NAME).buildObject(X509Data.DEFAULT_ELEMENT_NAME);
        X509Certificate cert = (X509Certificate) builderFactory.getBuilder(X509Certificate.DEFAULT_ELEMENT_NAME).buildObject(X509Certificate.DEFAULT_ELEMENT_NAME);
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

    private AuthnRequest parseSamlRequest(String samlRequestBase64) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(samlRequestBase64);
        ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
        
        InputStream is = bais;
        try {
            is = new InflaterInputStream(new ByteArrayInputStream(decodedBytes), new Inflater(true));
            byte[] testBytes = is.readNBytes(1);
            is = new InflaterInputStream(new ByteArrayInputStream(decodedBytes), new Inflater(true));
        } catch (Exception e) {
            is = new ByteArrayInputStream(decodedBytes);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document document = dbf.newDocumentBuilder().parse(is);
        Element element = document.getDocumentElement();

        Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(element);
        return (AuthnRequest) unmarshaller.unmarshall(element);
    }

    private Response buildSamlResponseContainer(String inResponseTo, String acsUrl) {
        XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();

        ResponseBuilder responseBuilder = (ResponseBuilder) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);
        Response response = responseBuilder.buildObject();
        response.setID("_" + UUID.randomUUID().toString());
        response.setInResponseTo(inResponseTo);
        response.setIssueInstant(Instant.now());
        response.setDestination(acsUrl);

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

        return response;
    }

    private String marshallAndBase64Encode(XMLObject xmlObject) throws Exception {
        Element element = xmlObject.getDOM();
        if (element == null) {
            Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
            element = marshaller.marshall(xmlObject);
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(element), new StreamResult(baos));
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private void marshallToWriter(XMLObject xmlObject, PrintWriter writer) throws Exception {
        Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
        Element element = marshaller.marshall(xmlObject);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(element), new StreamResult(writer));
    }
}