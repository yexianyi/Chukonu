package com.yxy.chukonu.java.multi.thread.deadlock.transfer.money;

/**
 * This solution will resolve potential deadlock possibilities.
 * The basic idea is to take advantage of ReentrantLock, which offers capability to try to obtain lock and return status at once,
 * so that the lock acquisition could be restarted before timeout.
 */
public class DeadLockSolution2 {
	
	private long waitDuration = 500000 ; //Milliseconds
	private long stopTime = System.currentTimeMillis() + waitDuration;
	
	public void transferMoney(Account fromAcct, Account toAcct, float amount) throws Exception {
		while(true){
			// Acquires the lock only if it is free at the time of invocation. 
			// Acquires the lock if it is available and returns immediately with the value true. 
			// If the lock is not available then this method will return immediately with the value false. 
			if(fromAcct.lock.tryLock()){
				try{
						if(toAcct.lock.tryLock()){
							try{
									if(fromAcct.getBalance()<amount){
										throw new Exception("No enough balance");
									}else{
										fromAcct.withdraw(amount);
										toAcct.deposit(amount);
										return ; //operation is done -> return ;
									}
							} finally{
								toAcct.lock.unlock();
							}
							
						}
				}finally{
					fromAcct.lock.unlock();
				}
			}//end if
			
			
			if(System.currentTimeMillis() > stopTime){
				return ; //waiting lock timeout -> give up operation
			}
		}
	}
				
}
	
