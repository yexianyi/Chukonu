package com.yxy.chukonu.java.multi.thread.deadlock.transfer.money;

/**
 * This solution will resolve potential deadlock possibilities.
 * The basic idea is to manually make sure the lock order is consistent whatever the situation it is.
 */
public class DeadLockSolution1 {
	
	private static final Object tieLock = new Object() ;
	
	public void transferMoney(Account fromAcct, Account toAcct, float amount) throws Exception {
		class Helper {
			public void doTransfer() throws Exception{
				if(fromAcct.getBalance()<amount){
					throw new Exception("No enough balance");
				}else{
					fromAcct.withdraw(amount);
					toAcct.deposit(amount);
				}
			}
		}
			
		//Returns the same hash code for the given object as would be returned by the default method hashCode(), whether or not the given object's class overrides hashCode(). 
		//The hash code for the null reference is zero.
		int fromHash = System.identityHashCode(fromAcct) ;
		int toHash = System.identityHashCode(toAcct) ;
		
		if(fromHash < toHash){
			synchronized(fromAcct){
				synchronized(toAcct){
					new Helper().doTransfer() ;
				}
			}
		}else if(fromHash > toHash){
			synchronized(toAcct){
				synchronized(fromAcct){
					new Helper().doTransfer() ;
				}
			}
		}else{//fromHash == toHash
			//synchronized(tieLock) is mandatory!!
			//if there is no tieLock to synchronize, 
			//there will be a chance that fromAcct and toAcct represent the opposite objects potentially, which leading to deadlock.
			synchronized(tieLock){
				synchronized(fromAcct){
					synchronized(toAcct){
						new Helper().doTransfer() ;
					}
				}
			}
		}
	}
				
}
	
