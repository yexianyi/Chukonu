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
package com.yxy.chukonu.java.multi.thread.rate.limit;

import java.util.concurrent.ArrayBlockingQueue;

public class TokenBucket {
	
	private int num_per_sec = 1 ;
	
	private int bucket_size = 10 ; 
	
	private long interval = getInterval() ;
	
	private ArrayBlockingQueue<Long> bucket = new ArrayBlockingQueue<Long>(bucket_size);
	
	
	public TokenBucket(int size, int rate) {
		bucket_size = size ;
		num_per_sec = rate ;
		interval = getInterval() ;
	}
	
	
	public Long getToken() throws InterruptedException {
		return bucket.poll() ;
	}
	
	public void addToken() {
		if(bucket.size()<bucket_size) {
			bucket.offer(System.currentTimeMillis()) ;
			System.out.println("Current bucket size:"+bucket.size()) ;
		}
		
	}
	

	public long getInterval() {
		return 1000/num_per_sec;
	}

	
	private static class Producer extends Thread{
		private TokenBucket bucket ;
		
		public Producer(TokenBucket bucket) {
			this.bucket = bucket ;
		}
		
		
		@Override
		public void run() {
			while(true) {
				try {
					bucket.addToken();
					Thread.sleep(bucket.getInterval());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	
	
}
