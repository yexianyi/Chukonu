package com.yxy.chukonu.java.multi.thread.rate.limit;

public class Consumer {

	public static void main(String[] args) {
		TokenBucket bucket = new TokenBucket(10, 1) ;
		Producer producer = new Producer(bucket) ;
		producer.start();
		
		int num = 5 ;
		
		for(int i=0; i<num; i++) {
			Thread consumer = new Thread() {
				@Override
				public void run() {
					while(true) {
						try {
							System.out.println("Thread:"+Thread.currentThread().getName()+" got token "+bucket.getToken());
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}
				
			};
			consumer.start();
		}//end for
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
