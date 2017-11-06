package com.yxy.chukonu.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.exceptions.InsufficientBalanceException;
import com.yxy.chukonu.mybatis.service.AccountService;

public class AccountServiceTestWithTx {
	
	private AccountService service ;
	
	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		service = ctx.getBean(AccountService.class) ;
	}
	
	@Test
	public void testDeposit() {
		String acctId = "aa889216-2dec-437b-87b0-1272d12a80c8" ;
		float original = service.getBalance(acctId) ;
		service.deposit(100f, acctId) ;
		float current = service.getBalance(acctId) ;
		assertEquals(original+100f, current, 0.01f) ;
	}
	
	@Test
	public void testWithdraw() throws Exception {
		String acctId = "aa889216-2dec-437b-87b0-1272d12a80c8" ;
		float original = service.getBalance(acctId) ;
		service.withdraw(100f, acctId) ;
		float current = service.getBalance(acctId) ;
		assertEquals(original-100f, current, 0.01f) ;
	}
	
	@Test(expected=InsufficientBalanceException.class) 
	public void testWithdrawException() throws Exception {
		service.withdraw(1000f, "aa889216-2dec-437b-87b0-1272d12a80c8") ;
	}
	
	
}
