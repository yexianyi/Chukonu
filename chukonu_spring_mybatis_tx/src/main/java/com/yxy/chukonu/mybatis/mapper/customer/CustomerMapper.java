package com.yxy.chukonu.mybatis.mapper.customer;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper {
	
	public Customer findCustomer(String user_uuid);
	
	

}
