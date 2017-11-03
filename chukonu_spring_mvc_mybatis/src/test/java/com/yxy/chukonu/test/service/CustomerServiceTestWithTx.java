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
