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

import com.yxy.chukonu.mybatis.mapper.customer.Customer;
import com.yxy.chukonu.mybatis.service.CustomerService;

public class CustomerServiceTestWithTx {
	
	private CustomerService service ;
	
	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		service = ctx.getBean(CustomerService.class) ;
	}
	
	@Test
	public void testQuery() {
		Customer result = service.findCustomer("652300f0-2a8b-4728-8704-3968a8819a5a") ;
		assertEquals("aaa", result.getName());
	}
}
