package com.yxy.chukonu.mybatis.service;

public interface AccountService {

	public float getBalance(String usrId);

	public void deposit(float amount, String usrId) ;
	
	public void withdraw(float amount, String usrId) ;

}
