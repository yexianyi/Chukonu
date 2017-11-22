package com.yxy.chukonu.java.multi.thread.deadlock.transfer.money;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	public Lock lock = new ReentrantLock() ;
	
	private float balance ;
	
	public void withdraw(float amount){
		balance -= amount ;
	}
	
	public void deposit(float amount){
		balance += amount ;
	}

	public float getBalance() {
		return balance;
	}
	
}
