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
