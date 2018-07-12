package com.yxy.chukonu.java.multi.thread.rate.limit;

public class Producer extends Thread{
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
