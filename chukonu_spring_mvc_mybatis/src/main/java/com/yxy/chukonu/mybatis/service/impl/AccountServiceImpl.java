package com.yxy.chukonu.mybatis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxy.chukonu.mybatis.mapper.account.AccountMapper;
import com.yxy.chukonu.mybatis.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	Logger log = Logger.getLogger(AccountServiceImpl.class) ;
	
	@Autowired
	private AccountMapper accountMapper ;
	
	@Override
	public float getBalance(String usrId) {
		
		return accountMapper.getBalance(usrId);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deposit(float amount, String usrId) {
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void withdraw(float amount, String usrId) {
		accountMapper.withdraw(amount, usrId);
		float remaining = accountMapper.getBalance(usrId) ;
		if(remaining<0){
			throw new RuntimeException("No enough balance") ;
		}
	}

}
