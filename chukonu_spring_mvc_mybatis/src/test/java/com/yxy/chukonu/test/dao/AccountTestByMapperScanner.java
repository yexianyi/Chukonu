package com.yxy.chukonu.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.mapper.account.Account;
import com.yxy.chukonu.mybatis.mapper.account.AccountMapper;

public class AccountTestByMapperScanner {
	
	private AccountMapper mapper ;

	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		mapper = ctx.getBean(AccountMapper.class) ;
	}
	
	@Test
	public void testFindAccount() {
		Account result = mapper.findAccount("aa889216-2dec-437b-87b0-1272d12a80c8") ;
		assertEquals("1000100010001000", result.getAcc_number());
	}
	
	@Test
	public void testQueryBalance() {
		float result = mapper.getBalance("652300f0-2a8b-4728-8704-3968a8819a5a") ;
		assertEquals(200, result, 0.01);
	}
	
	@Test
	public void testDeposit() {
		float original = mapper.getBalance("652300f0-2a8b-4728-8704-3968a8819a5a") ;
		mapper.deposit(100f, "652300f0-2a8b-4728-8704-3968a8819a5a") ;
		float result = mapper.getBalance("652300f0-2a8b-4728-8704-3968a8819a5a") ;
		assertEquals(original+100, result, 0.01);
	}
	
	@Test
	public void testWithdraw() {
		float original = mapper.getBalance("652300f0-2a8b-4728-8704-3968a8819a5a") ;
		mapper.withdraw(100f, "652300f0-2a8b-4728-8704-3968a8819a5a") ;
		float result = mapper.getBalance("652300f0-2a8b-4728-8704-3968a8819a5a") ;
		assertEquals(original-100, result, 0.01);
	}
	
	
}
