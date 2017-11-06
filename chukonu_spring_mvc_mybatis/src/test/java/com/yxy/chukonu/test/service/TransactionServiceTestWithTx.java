package com.yxy.chukonu.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.exceptions.InsufficientBalanceException;
import com.yxy.chukonu.mybatis.service.AccountService;
import com.yxy.chukonu.mybatis.service.TransactionService;

public class TransactionServiceTestWithTx {
	
	private TransactionService txService ;
	private AccountService acctService ;
	
	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		txService = ctx.getBean(TransactionService.class) ;
		acctService = ctx.getBean(AccountService.class) ;
	}
	
	@Test
	public void testDoTransaction() {
		String fromAcctNum = "2000200020002000" ;
		String toAcctNum = "1000100010001000" ;
		float amount = 100f ;
		float originalFrom = acctService.getBalance(fromAcctNum) ;
		float originalTo = acctService.getBalance(toAcctNum) ;
		txService.doTransaction(fromAcctNum, toAcctNum, amount);
		float currentFrom = acctService.getBalance(fromAcctNum) ;
		float currentTo = acctService.getBalance(toAcctNum) ;
		assertEquals(originalFrom-amount, currentFrom, 0.01f) ;
		assertEquals(originalTo+amount, currentTo, 0.01f) ;
	}
	
	@Test(expected=InsufficientBalanceException.class) 
	public void testDoTransactionException() {
		String fromAcctNum = "2000200020002000" ;
		String toAcctNum = "1000100010001000" ;
		float amount = 1000f ;
		float originalFrom = acctService.getBalance(fromAcctNum) ;
		float originalTo = acctService.getBalance(toAcctNum) ;
		txService.doTransaction(fromAcctNum, toAcctNum, amount);
		float currentFrom = acctService.getBalance(fromAcctNum) ;
		float currentTo = acctService.getBalance(toAcctNum) ;
		assertEquals(originalFrom, currentFrom, 0.01f) ;
		assertEquals(originalTo, currentTo, 0.01f) ;
	}
}
