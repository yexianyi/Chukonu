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
