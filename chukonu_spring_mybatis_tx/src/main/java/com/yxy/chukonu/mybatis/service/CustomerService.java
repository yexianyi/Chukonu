package com.yxy.chukonu.mybatis.service;

import com.yxy.chukonu.mybatis.mapper.customer.Customer;

public interface CustomerService {

	public Customer findCustomer(String user_uuid);
	
}
