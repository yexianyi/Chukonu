package com.yxy.chukonu.sso.saml.sp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxy.chukonu.sso.saml.sp.model.LocalUser;
import com.yxy.chukonu.sso.saml.sp.service.LocalUserService;

import jakarta.annotation.Resource;

// 注意：改为@Controller（而非@RestController），支持页面跳转
@Controller
public class IndexController {

    @Resource
    private LocalUserService localUserService;

    // 首页（跳转页面）
    @GetMapping("/")
    public String index() {
        return "index"; // 对应templates/index.html
    }

    /**
     * 用户信息页面（渲染profile.html）
     */
    @GetMapping("/user")
    public String userProfile(@AuthenticationPrincipal Saml2AuthenticatedPrincipal samlPrincipal,
                              Model model) {
        if (samlPrincipal == null) {
            return "redirect:/"; // 未登录则跳回首页
        }

        // 1. SAML身份信息
        String samlNameId = samlPrincipal.getName();
        model.addAttribute("samlNameId", samlNameId);
        model.addAttribute("samlAttributes", samlPrincipal.getAttributes());

        // 2. 本地用户信息
        LocalUser localUser = localUserService.getLocalUserBySamlNameId(samlNameId);
        if (localUser == null) {
            localUser = localUserService.createDefaultLocalUser(samlNameId);
        }
        model.addAttribute("localUser", localUser);

        return "profile"; // 对应templates/profile.html
    }

    /**
     * 订单创建页面（渲染create-order.html）
     */
    @GetMapping("/order/create")
    public String createOrder(@AuthenticationPrincipal Saml2AuthenticatedPrincipal samlPrincipal,
                              Model model) {
        Map<String, Object> result = new HashMap<>();
        if (samlPrincipal == null) {
            return "redirect:/"; // 未登录则跳回首页
        }

        // 1. 关联本地用户
        LocalUser localUser = localUserService.getLocalUserBySamlNameId(samlPrincipal.getName());
        if (localUser == null) {
            model.addAttribute("status", "forbidden");
            model.addAttribute("message", "本地无此用户，请联系管理员");
            return "create-order";
        }

        // 2. 校验本地权限
        if (localUser.getPermissions().contains("order:create")) {
            model.addAttribute("status", "success");
            model.addAttribute("message", "订单创建成功（用户：" + localUser.getUsername() + "）");
            model.addAttribute("orderId", "ORD-" + System.currentTimeMillis());
        } else {
            model.addAttribute("status", "forbidden");
            model.addAttribute("message", "无订单创建权限（用户：" + localUser.getUsername() + "）");
        }

        return "create-order"; // 对应templates/create-order.html
    }

    // 保留原有JSON接口（可选，用于API测试）
    @GetMapping("/user/api")
    @ResponseBody
    public Map<String, Object> userApi(@AuthenticationPrincipal Saml2AuthenticatedPrincipal samlPrincipal) {
        Map<String, Object> result = new HashMap<>();
        if (samlPrincipal != null) {
            result.put("NameID", samlPrincipal.getName());
            result.put("Attributes", samlPrincipal.getAttributes());
            LocalUser localUser = localUserService.getLocalUserBySamlNameId(samlPrincipal.getName());
            if (localUser == null) {
                localUser = localUserService.createDefaultLocalUser(samlPrincipal.getName());
            }
            result.put("LocalUser", localUser);
        } else {
            result.put("error", "未认证");
        }
        return result;
    }
}