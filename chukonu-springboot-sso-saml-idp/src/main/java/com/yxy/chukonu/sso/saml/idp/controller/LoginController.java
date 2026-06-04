package com.yxy.chukonu.sso.saml.idp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yxy.chukonu.sso.saml.idp.entity.SamlUser;
import com.yxy.chukonu.sso.saml.idp.service.UserAuthService;

import jakarta.servlet.http.HttpSession;

/**
 * 登录页面控制器
 */
@Controller
public class LoginController {

    private final UserAuthService userAuthService;

    public LoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    /**
     * 跳转登录页
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 登录提交处理
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        SamlUser user = userAuthService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("samlUser", user);
            return "redirect:/saml/sso";
        }
        model.addAttribute("error", "用户名或密码错误");
        return "login";
    }
}