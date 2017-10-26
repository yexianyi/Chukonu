package com.yxy.chukonu.test.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yxy.chukonu.mybatis.mapper.Customer;

public class CustomerTest {
	
	public static void main(String[] args){
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Customer customer = (Customer) session.selectOne("findCustomer", "2");
			System.out.println(customer.getName());
		} finally {
		  session.close();
		}
	}

}
