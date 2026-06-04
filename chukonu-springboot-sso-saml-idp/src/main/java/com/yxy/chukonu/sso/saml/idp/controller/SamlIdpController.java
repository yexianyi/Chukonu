package com.yxy.chukonu.sso.saml.idp.controller;

import java.io.PrintWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml.saml2.metadata.impl.EntityDescriptorBuilder;
import org.opensaml.saml.saml2.metadata.impl.IDPSSODescriptorBuilder;
import org.opensaml.saml.saml2.metadata.impl.SingleSignOnServiceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Element;

import com.yxy.chukonu.sso.saml.idp.entity.SamlUser;
import com.yxy.chukonu.sso.saml.idp.service.SamlAssertionService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/saml")
public class SamlIdpController {

    @Value("${saml.idp.entity-id}")
    private String idpEntityId;

    @Value("${saml.idp.sso-url}")
    private String ssoUrl;

    private final SamlAssertionService samlAssertionService;

    public SamlIdpController(SamlAssertionService samlAssertionService) {
        this.samlAssertionService = samlAssertionService;
    }

    // 元数据接口
    @GetMapping("/metadata")
    public void metadata(HttpServletResponse response) throws Exception {
        response.setContentType("application/xml;charset=UTF-8");

        EntityDescriptor entityDescriptor = new EntityDescriptorBuilder()
                .buildObject(EntityDescriptor.DEFAULT_ELEMENT_NAME);
        entityDescriptor.setEntityID(idpEntityId);

        IDPSSODescriptor idpSsoDescriptor = new IDPSSODescriptorBuilder()
                .buildObject(IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        idpSsoDescriptor.setWantAuthnRequestsSigned(false);

        SingleSignOnService ssoService = new SingleSignOnServiceBuilder()
                .buildObject(SingleSignOnService.DEFAULT_ELEMENT_NAME);
        ssoService.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect");
        ssoService.setLocation(ssoUrl);

        idpSsoDescriptor.getSingleSignOnServices().add(ssoService);
        entityDescriptor.getRoleDescriptors().add(idpSsoDescriptor);

        try (PrintWriter writer = response.getWriter()) {
            marshallToWriter(entityDescriptor, writer);
        }
    }

    // 单点登录
    @GetMapping("/sso")
    public void sso(@RequestParam(required = false) String spEntityId,
                    HttpSession session,
                    HttpServletResponse response) throws Exception {

        SamlUser user = (SamlUser) session.getAttribute("samlUser");
        if (user == null) {
            response.sendRedirect("/login");
            return;
        }

        String spId = spEntityId == null ? "https://sp.chukonu.com" : spEntityId;
        Assertion assertion = samlAssertionService.generateSamlAssertion(user, spId);

        response.setContentType("application/xml;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            marshallToWriter(assertion, writer);
        }
    }

    private void marshallToWriter(XMLObject xmlObject, PrintWriter writer) throws Exception {
        Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
        Element element = marshaller.marshall(xmlObject);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(element), new StreamResult(writer));
    }
}