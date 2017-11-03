package com.yxy.chukonu.test.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.service.AccountService;

public class AccountServiceTestWithTx {
	
	private AccountService service ;
	
	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		service = ctx.getBean(AccountService.class) ;
	}
	
	@Test(expected=RuntimeException.class) 
	public void testWithdrawException() {
		service.withdraw(1000f, "652300f0-2a8b-4728-8704-3968a8819a5a") ;
	}
}
