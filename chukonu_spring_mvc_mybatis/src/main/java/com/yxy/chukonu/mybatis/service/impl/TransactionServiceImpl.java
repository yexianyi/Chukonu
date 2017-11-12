package com.yxy.chukonu.mybatis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxy.chukonu.mybatis.service.AccountService;
import com.yxy.chukonu.mybatis.service.TransactionService;

@Service
@EnableAspectJAutoProxy
public class TransactionServiceImpl implements TransactionService{

	Logger log = Logger.getLogger(TransactionServiceImpl.class) ;

	@Autowired
	public AccountService acctService ;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void doTransaction(String fromAcct, String toAcct, float txAmount) {
		acctService.withdraw(txAmount, fromAcct);
		acctService.deposit(txAmount, toAcct);
	}


}
