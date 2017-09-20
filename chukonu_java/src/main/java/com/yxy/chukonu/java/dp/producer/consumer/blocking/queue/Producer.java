package com.yxy.chukonu.java.dp.producer.consumer.blocking.queue;

import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

	public static AtomicInteger counter = new AtomicInteger(0) ;
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			try {
				Global.queue.put(counter.incrementAndGet()) ;
				System.out.println("Producer#"+Thread.currentThread().getId()+": produced "+counter.get());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}
	
	public boolean terminate(){
		Thread.currentThread().interrupt();
		return true ;
	}

}
