package com.yxy.chukonu.mybatis.service;

public interface TransactionService {

	public void doTransaction(String fromAcct, String toAcct, float txAmount) ;
}
