/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
