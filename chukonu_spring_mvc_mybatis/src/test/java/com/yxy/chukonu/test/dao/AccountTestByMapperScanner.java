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
		float result = mapper.getBalance("1000100010001000") ;
		assertEquals(200, result, 0.01);
	}
	
	@Test
	public void testDeposit() {
		String acctNum = "1000100010001000" ;
		float original = mapper.getBalance(acctNum) ;
		mapper.deposit(100f, acctNum) ;
		float result = mapper.getBalance(acctNum) ;
		assertEquals(original+100, result, 0.01);
	}
	
	@Test
	public void testWithdraw() {
		String acctNum = "1000100010001000" ;
		float original = mapper.getBalance(acctNum) ;
		mapper.withdraw(100f, acctNum) ;
		float result = mapper.getBalance(acctNum) ;
		assertEquals(original-100, result, 0.01);
	}
	
	
}
