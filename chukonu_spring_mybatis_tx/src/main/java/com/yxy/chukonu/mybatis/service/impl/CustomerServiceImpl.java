package com.yxy.chukonu.mybatis.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxy.chukonu.mybatis.mapper.customer.Customer;
import com.yxy.chukonu.mybatis.mapper.customer.CustomerMapper;
import com.yxy.chukonu.mybatis.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	Logger log = Logger.getLogger(CustomerServiceImpl.class) ;

	@Autowired
	private CustomerMapper customerMapper ;
	
	@Override
	@Transactional(propagation = Propagation.NEVER, isolation = Isolation.READ_COMMITTED)
	public Customer findCustomer(String user_uuid) {
		return customerMapper.findCustomer(user_uuid) ;
	}

}
