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

import com.yxy.chukonu.mybatis.mapper.customer.Customer;
import com.yxy.chukonu.mybatis.mapper.customer.CustomerMapper;

public class CustomerTest {
	
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
		Customer result = (Customer) session.selectOne("findCustomer", "2");
		assertEquals("aaa", result.getName());
	}
	
	
	@Test
	public void testQueryByMapper() {
		CustomerMapper mapper = session.getMapper(CustomerMapper.class) ;
		Customer result = mapper.findCustomer("2") ;
		assertEquals("aaa", result.getName());
	}
	
	
	@After
	public void closeSession(){
		if(session != null){
			session.close();
		}
	}
	

}
