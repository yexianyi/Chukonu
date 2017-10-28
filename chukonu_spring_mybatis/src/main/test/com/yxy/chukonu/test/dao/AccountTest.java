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

import com.yxy.chukonu.mybatis.mapper.account.Account;
import com.yxy.chukonu.mybatis.mapper.account.AccountMapper;
import com.yxy.chukonu.mybatis.mapper.customer.Customer;
import com.yxy.chukonu.mybatis.mapper.customer.CustomerMapper;

public class AccountTest {
	
	private SqlSession session ;
	
	@Before
	public void createSession(){
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		session = sqlSessionFactory.openSession();
	}
	
	
	@Test
	public void testQueryBySqlSession() {
		Account result = (Account) session.selectOne("findAccount", "aa889216-2dec-437b-87b0-1272d12a80c8");
		assertEquals("1000100010001000", result.getAcc_number());
	}
	
	
	@Test
	public void testQueryByMapper() {
		AccountMapper mapper = session.getMapper(AccountMapper.class) ;
		Account result = mapper.findAccount("89ab5a34-0e07-43e2-a5e6-8b0ad8446a26") ;
		assertEquals("2000200020002000", result.getAcc_number());
	}
	
	
	@After
	public void closeSession(){
		if(session != null){
			session.close();
		}
	}
	

}
