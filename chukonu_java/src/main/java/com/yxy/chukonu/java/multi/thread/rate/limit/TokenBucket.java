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
