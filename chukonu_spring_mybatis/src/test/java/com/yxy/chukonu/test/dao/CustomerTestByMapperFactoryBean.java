package com.yxy.chukonu.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.mapper.customer.Customer;
import com.yxy.chukonu.mybatis.mapper.customer.CustomerMapper;

public class CustomerTestByMapperFactoryBean {
	
	private CustomerMapper mapper ;

	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis-mapperFactoryBean.xml") ;
		mapper = ctx.getBean(CustomerMapper.class) ;
	}
	
	@Test
	public void testQuery() {
		Customer result = (Customer) mapper.findCustomer("652300f0-2a8b-4728-8704-3968a8819a5a") ;
		assertEquals("aaa", result.getName());
	}
}
