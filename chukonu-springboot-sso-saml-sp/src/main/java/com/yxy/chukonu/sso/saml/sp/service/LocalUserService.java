package com.yxy.chukonu.sso.saml.sp.service;

import com.yxy.chukonu.sso.saml.sp.model.LocalUser;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 模拟本地用户数据服务
 * 实际场景可替换为数据库/缓存查询
 */
@Service
public class LocalUserService {

    // 模拟本地用户库（key: SAML NameID）
    private static final Map<String, LocalUser> LOCAL_USER_DB = new HashMap<>();

    // 初始化模拟数据
    static {
        LOCAL_USER_DB.put("user1@example.com", new LocalUser(
                "user1@example.com",
                "张三",
                Set.of("user:view", "order:create", "report:export"),
                "研发部"
        ));
        LOCAL_USER_DB.put("user2@example.com", new LocalUser(
                "user2@example.com",
                "李四",
                Set.of("user:view", "order:view"),
                "财务部"
        ));
    }

    /**
     * 根据SAML返回的NameID查询本地用户
     */
    public LocalUser getLocalUserBySamlNameId(String samlNameId) {
        return LOCAL_USER_DB.get(samlNameId);
    }

    /**
     * 模拟：如果SAML用户首次登录，自动创建本地基础用户（可选）
     */
    public LocalUser createDefaultLocalUser(String samlNameId) {
        LocalUser defaultUser = new LocalUser(
                samlNameId,
                "默认用户-" + samlNameId.split("@")[0],
                Set.of("user:view"),
                "默认部门"
        );
        LOCAL_USER_DB.put(samlNameId, defaultUser);
        return defaultUser;
    }
}