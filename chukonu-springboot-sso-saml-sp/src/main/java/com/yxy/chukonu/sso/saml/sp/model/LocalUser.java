package com.yxy.chukonu.sso.saml.sp.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 本地用户实体（模拟数据库存储） 包含SAML关联ID、基础信息、本地权限
 */
@Data
@AllArgsConstructor
public class LocalUser {
	// 与SAML NameID关联的唯一标识
	private String samlNameId;
	// 本地用户名
	private String username;
	// 本地角色/权限
	private Set<String> permissions;
	// 本地业务属性（示例）
	private String department;
}