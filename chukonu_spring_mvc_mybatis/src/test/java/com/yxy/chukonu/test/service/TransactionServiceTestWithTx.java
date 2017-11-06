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
		String fromAcctId = "89ab5a34-0e07-43e2-a5e6-8b0ad8446a26" ;
		String toAcctId = "aa889216-2dec-437b-87b0-1272d12a80c8" ;
		float amount = 100f ;
		float originalFrom = acctService.getBalance(fromAcctId) ;
		float originalTo = acctService.getBalance(toAcctId) ;
		txService.doTransaction(fromAcctId, toAcctId, amount);
		float currentFrom = acctService.getBalance(fromAcctId) ;
		float currentTo = acctService.getBalance(toAcctId) ;
		assertEquals(originalFrom-amount, currentFrom, 0.01f) ;
		assertEquals(originalTo+amount, currentTo, 0.01f) ;
	}
	
	@Test(expected=InsufficientBalanceException.class) 
	public void testDoTransactionException() {
		String fromAcctId = "89ab5a34-0e07-43e2-a5e6-8b0ad8446a26" ;
		String toAcctId = "aa889216-2dec-437b-87b0-1272d12a80c8" ;
		float amount = 1000f ;
		float originalFrom = acctService.getBalance(fromAcctId) ;
		float originalTo = acctService.getBalance(toAcctId) ;
		txService.doTransaction(fromAcctId, toAcctId, amount);
		float currentFrom = acctService.getBalance(fromAcctId) ;
		float currentTo = acctService.getBalance(toAcctId) ;
		assertEquals(originalFrom, currentFrom, 0.01f) ;
		assertEquals(originalTo, currentTo, 0.01f) ;
	}
}
