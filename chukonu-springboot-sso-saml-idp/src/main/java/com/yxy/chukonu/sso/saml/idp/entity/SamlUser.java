package com.yxy.chukonu.sso.saml.idp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SAML 认证用户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SamlUser {
    // 用户名（唯一标识）
    private String username;
    // 密码
    private String password;
    // 邮箱
    private String email;
    // 姓名
    private String name;
}