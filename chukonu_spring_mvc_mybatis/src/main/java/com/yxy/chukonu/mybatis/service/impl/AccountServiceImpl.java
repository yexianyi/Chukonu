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
package com.yxy.chukonu.mybatis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxy.chukonu.mybatis.exceptions.InsufficientBalanceException;
import com.yxy.chukonu.mybatis.mapper.account.AccountMapper;
import com.yxy.chukonu.mybatis.service.AccountService;

@Service
@EnableAspectJAutoProxy
public class AccountServiceImpl implements AccountService {
	Logger log = Logger.getLogger(AccountServiceImpl.class) ;
	
	@Autowired
	private AccountMapper accountMapper ;
	
	@Override
	public float getBalance(String acctNum) {
		
		return accountMapper.getBalance(acctNum);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deposit(float amount, String acctNum) {
		accountMapper.deposit(amount, acctNum) ;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void withdraw(float amount, String acctNum) {
		accountMapper.withdraw(amount, acctNum);
		float remaining = accountMapper.getBalance(acctNum) ;
		if(remaining<0){
			throw new InsufficientBalanceException() ;
		}
		
	}

}
