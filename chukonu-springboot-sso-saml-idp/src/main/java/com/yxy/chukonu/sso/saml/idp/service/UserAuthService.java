package com.yxy.chukonu.sso.saml.idp.service;


import org.springframework.stereotype.Service;
import com.yxy.chukonu.sso.saml.idp.entity.SamlUser;

/**
 * 用户认证服务（模拟数据库认证）
 */
@Service
public class UserAuthService {

    /**
     * 用户登录认证
     */
    public SamlUser authenticate(String username, String password) {
        // 模拟用户校验（实际业务中查询数据库）
        if ("admin".equals(username) && "123456".equals(password)) {
            return new SamlUser("admin", "123456", "admin@chukonu.com", "系统管理员");
        }
        return null;
    }
}