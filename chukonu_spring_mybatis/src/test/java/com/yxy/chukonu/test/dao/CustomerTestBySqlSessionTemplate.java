package com.yxy.chukonu.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.mapper.customer.Customer;

public class CustomerTestBySqlSessionTemplate {
	
	private SqlSessionTemplate sqlSessionTemplate ;
	
	@Before
	public void createSessionTemplate(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis-sqlSessionTemplate.xml") ;
		sqlSessionTemplate = ctx.getBean(SqlSessionTemplate.class) ;
	}
	
	
	@Test
	public void testQuery() {
		Customer customer = new Customer() ;
		customer.setId("652300f0-2a8b-4728-8704-3968a8819a5a");
		
		Customer result = (Customer) sqlSessionTemplate.selectOne("com.yxy.chukonu.mybatis.mapper.customer.CustomerMapper.findCustomer",customer);
		assertEquals("aaa", result.getName());
	}
	
	
}
