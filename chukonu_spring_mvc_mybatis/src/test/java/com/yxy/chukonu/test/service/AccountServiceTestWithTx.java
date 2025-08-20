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

public class AccountServiceTestWithTx {
	
	private AccountService service ;
	
	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		service = ctx.getBean(AccountService.class) ;
	}
	
	@Test
	public void testDeposit() {
		String acctNum = "1000100010001000" ;
		float original = service.getBalance(acctNum) ;
		service.deposit(100f, acctNum) ;
		float current = service.getBalance(acctNum) ;
		assertEquals(original+100f, current, 0.01f) ;
	}
	
	@Test
	public void testWithdraw() throws Exception {
		String acctNum = "1000100010001000" ;
		float original = service.getBalance(acctNum) ;
		service.withdraw(100f, acctNum) ;
		float current = service.getBalance(acctNum) ;
		assertEquals(original-100f, current, 0.01f) ;
	}
	
	@Test(expected=InsufficientBalanceException.class) 
	public void testWithdrawException() throws Exception {
		service.withdraw(1000f, "1000100010001000") ;
	}
	
	
}
