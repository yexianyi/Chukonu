package com.yxy.chukonu.sso.saml.sp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.metadata.OpenSaml4MetadataResolver;
import org.springframework.security.saml2.provider.service.metadata.Saml2MetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
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

	// 注入 SP 本身的 RelyingParty 注册仓库（用于读取 application.yml 的凭证配置）
	@Resource
	private RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

	private final Saml2MetadataResolver metadataResolver = new OpenSaml4MetadataResolver();

	// 首页（跳转页面）
	@GetMapping("/")
	public String index() {
		return "index";
	}

	
	@GetMapping("/user")
	public String profile(@AuthenticationPrincipal Saml2AuthenticatedPrincipal samlPrincipal, Model model) {
	    if (samlPrincipal != null) {
	        // 查询/创建本地用户
	        LocalUser localUser = localUserService.getLocalUserBySamlNameId(samlPrincipal.getName());
	        if (localUser == null) {
	            localUser = localUserService.createDefaultLocalUser(samlPrincipal.getName());
	        }
	        model.addAttribute("localUser", localUser);
	        model.addAttribute("samlNameId", samlPrincipal.getName());
	        model.addAttribute("samlAttributes", samlPrincipal.getAttributes());
	    }
		return "profile";
	}
	
	
	/**
	 * 显式实现 SP 端元数据暴露接口 访问路径：http://localhost:8090/sp/saml/metadata
	 */
	@GetMapping(value = "/saml/metadata", produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String getSpMetadata() {
		// 根据你 application.yml 里的注册 ID 'chukonu' 提取出完整的 SP 端基础配置
		RelyingPartyRegistration registration = relyingPartyRegistrationRepository.findByRegistrationId("chukonu");
		if (registration == null) {
			throw new IllegalArgumentException("未找到凭证注册ID为 [chukonu] 的配置");
		}
		// 解析器会自动将你的 EntityID、ACS、证书等拼装成规范的标准 SAML2 元数据 XML 返回
		return this.metadataResolver.resolve(registration);
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