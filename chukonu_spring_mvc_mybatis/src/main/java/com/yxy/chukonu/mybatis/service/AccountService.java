package com.yxy.chukonu.mybatis.service;

public interface AccountService {

	public float getBalance(String acctNum);

	public void deposit(float amount, String acctNum) ;
	
	public void withdraw(float amount, String acctNum) ;

}
