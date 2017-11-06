package com.yxy.chukonu.mybatis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxy.chukonu.mybatis.exceptions.InsufficientBalanceException;
import com.yxy.chukonu.mybatis.mapper.account.AccountMapper;
import com.yxy.chukonu.mybatis.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	Logger log = Logger.getLogger(AccountServiceImpl.class) ;
	
	@Autowired
	private AccountMapper accountMapper ;
	
	@Override
	public float getBalance(String acctId) {
		
		return accountMapper.getBalance(acctId);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deposit(float amount, String acctId) {
		accountMapper.deposit(amount, acctId) ;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void withdraw(float amount, String acctId) {
		accountMapper.withdraw(amount, acctId);
		float remaining = accountMapper.getBalance(acctId) ;
		if(remaining<0){
			throw new InsufficientBalanceException() ;
		}
		
	}

}
