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
		Customer result = (Customer) session.selectOne("findCustomer", "652300f0-2a8b-4728-8704-3968a8819a5a");
		assertEquals("aaa", result.getName());
	}
	
	
	@Test
	public void testQueryByMapper() {
		CustomerMapper mapper = session.getMapper(CustomerMapper.class) ;
		Customer result = mapper.findCustomer("e92c3808-bea4-4fc3-9877-c71c31c5709d") ;
		assertEquals("bbb", result.getName());
	}
	
	
	@After
	public void closeSession(){
		if(session != null){
			session.close();
		}
	}
	

}
