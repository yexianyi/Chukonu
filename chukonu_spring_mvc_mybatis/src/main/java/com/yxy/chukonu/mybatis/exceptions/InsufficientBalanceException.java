package com.yxy.chukonu.mybatis.exceptions;

public class InsufficientBalanceException extends RuntimeException {

	public InsufficientBalanceException() {
		super("Insufficient Balance");
	}

}
