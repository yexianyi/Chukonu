package com.yxy.chukonu.mybatis.service;

public interface AccountService {

	public float getBalance(String acctId);

	public void deposit(float amount, String acctId) ;
	
	public void withdraw(float amount, String acctId) ;

}
