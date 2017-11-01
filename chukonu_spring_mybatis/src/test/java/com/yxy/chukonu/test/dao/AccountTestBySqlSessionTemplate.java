package com.yxy.chukonu.test.dao;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yxy.chukonu.mybatis.mapper.account.Account;
import com.yxy.chukonu.mybatis.mapper.account.AccountMapper;
import com.yxy.chukonu.mybatis.mapper.customer.Customer;
import com.yxy.chukonu.mybatis.mapper.customer.CustomerMapper;

public class AccountTestBySqlSessionTemplate {
	
	private SqlSessionTemplate sqlSessionTemplate ;
	
	@Before
	public void createSession(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis-sqlSessionTemplate.xml") ;
		sqlSessionTemplate = ctx.getBean(SqlSessionTemplate.class) ;
	}
	
	
	@Test
	public void testQuery() {
		Account account = new Account() ;
		account.setId("aa889216-2dec-437b-87b0-1272d12a80c8");
		
		Account result = (Account) sqlSessionTemplate.selectOne("com.yxy.chukonu.mybatis.mapper.account.AccountMapper.findAccount",account);
		assertEquals("1000100010001000", result.getAcc_number());
	}
	

}
