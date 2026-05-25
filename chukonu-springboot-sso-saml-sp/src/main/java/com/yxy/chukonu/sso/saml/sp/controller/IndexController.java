package com.yxy.chukonu.sso.saml.sp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Chukonu SAML Service Provider! Please visit <a href='/user'>/user</a> to trigger SSO.";
    }

    @GetMapping("/user")
    public Map<String, Object> userDetail(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
        Map<String, Object> details = new HashMap<>();
        if (principal != null) {
            details.put("NameID (Subject)", principal.getName());
            details.put("Attributes", principal.getAttributes());
            details.put("RelyingPartyRegistrationId", principal.getRelyingPartyRegistrationId());
        } else {
            details.put("error", "Principal is null");
        }
        return details;
    }
}