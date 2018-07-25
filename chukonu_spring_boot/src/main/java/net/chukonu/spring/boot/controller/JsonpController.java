package net.chukonu.spring.boot.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.chukonu.spring.boot.controller.param.UserInfo;

@RestController
public class JsonpController {
	
	@RequestMapping(value="/jsonp/user", method = RequestMethod.GET, produces="application/json")
	public UserInfo getUser() {
		UserInfo user = new UserInfo("test", "test", new ArrayList<String>(), "BMW") ;
		return user;
	}
	
	@RequestMapping(value="/jsonp/user2", method = RequestMethod.GET, produces="application/json")
	public Object getUser2() {
		UserInfo user = new UserInfo("test", "test", new ArrayList<String>(), "BMW") ;
		JSONPObject obj = new JSONPObject("callback", user) ;
		return obj;
	}
	
}
