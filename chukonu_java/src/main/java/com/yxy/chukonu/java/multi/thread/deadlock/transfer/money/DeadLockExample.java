package com.yxy.chukonu.java.multi.thread.deadlock.transfer.money;

/**
 * Deadlock would occur if two threads call transferMoney at the same time, 
 * one transferring from A to B, and the other doing the oppsite:
 * A: transferMoney(account_A, account_B, 10) ;
 * B: transferMoney(account_B, account_A, 10) ;
 */
public class DeadLockExample {
	
	public void transferMoney(Account fromAcct, Account toAcct, float amount) throws Exception{
		synchronized(fromAcct){
			synchronized(toAcct){
				if(fromAcct.getBalance()<amount){
					throw new Exception("No enough balance");
				}else{
					fromAcct.withdraw(amount);
					toAcct.deposit(amount);
				}
			}
		}
	}

	
}
