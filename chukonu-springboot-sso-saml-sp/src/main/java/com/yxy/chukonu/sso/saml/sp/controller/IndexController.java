package com.yxy.chukonu.sso.saml.sp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yxy.chukonu.sso.saml.sp.model.LocalUser;
import com.yxy.chukonu.sso.saml.sp.service.LocalUserService;

import jakarta.annotation.Resource;

@RestController
public class IndexController {

    @Resource
    private LocalUserService localUserService;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Chukonu SAML Service Provider! Please visit <a href='/user'>/user</a> to trigger SSO.";
    }

    /**
     * 整合SAML身份 + 本地用户权限
     */
    @GetMapping("/user")
    public Map<String, Object> userDetail(@AuthenticationPrincipal Saml2AuthenticatedPrincipal samlPrincipal) {
        Map<String, Object> result = new HashMap<>();

        // 1. SAML IDP返回的身份信息（统一身份认证结果）
        if (samlPrincipal != null) {
            result.put("=== SAML IDP 统一身份信息 ===", null);
            result.put("NameID (Subject)", samlPrincipal.getName());
            result.put("SAML Attributes", samlPrincipal.getAttributes());
            result.put("RelyingPartyRegistrationId", samlPrincipal.getRelyingPartyRegistrationId());

            // 2. 关联本地用户（核心：用SAML的NameID查询本地用户）
            String samlNameId = samlPrincipal.getName();
            LocalUser localUser = localUserService.getLocalUserBySamlNameId(samlNameId);
            
            // 首次登录自动创建本地用户（可选逻辑）
            if (localUser == null) {
                localUser = localUserService.createDefaultLocalUser(samlNameId);
                result.put("提示", "首次登录，已自动创建本地基础用户");
            }

            // 3. 本地系统的用户权限/业务数据（体现SP侧自主性）
            result.put("=== 本地系统用户信息 ===", null);
            result.put("本地用户名", localUser.getUsername());
            result.put("本地权限", localUser.getPermissions());
            result.put("所属部门", localUser.getDepartment());

        } else {
            result.put("error", "未通过SAML IDP认证");
        }
        return result;
    }

    /**
     * 示例：基于本地权限的接口控制（体现权限价值）
     */
    @GetMapping("/order/create")
    public Map<String, Object> createOrder(@AuthenticationPrincipal Saml2AuthenticatedPrincipal samlPrincipal) {
        Map<String, Object> result = new HashMap<>();
        if (samlPrincipal == null) {
            result.put("error", "请先通过SAML SSO认证");
            return result;
        }

        // 1. 关联本地用户
        LocalUser localUser = localUserService.getLocalUserBySamlNameId(samlPrincipal.getName());
        if (localUser == null) {
            result.put("error", "本地无此用户，请联系管理员");
            return result;
        }

        // 2. 校验本地权限（核心：权限由SP自主管理）
        if (localUser.getPermissions().contains("order:create")) {
            result.put("status", "success");
            result.put("message", "订单创建成功（用户：" + localUser.getUsername() + "）");
            result.put("orderId", "ORD-" + System.currentTimeMillis());
        } else {
            result.put("status", "forbidden");
            result.put("message", "无订单创建权限（用户：" + localUser.getUsername() + "）");
        }
        return result;
    }
}