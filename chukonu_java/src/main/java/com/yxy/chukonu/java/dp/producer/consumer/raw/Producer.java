package com.yxy.chukonu.java.dp.producer.consumer.raw;

import java.util.concurrent.atomic.AtomicInteger;


public class Producer extends Thread {
	
	public static AtomicInteger counter = new AtomicInteger(0) ;
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			synchronized(Global.queue){
				while(Global.queue.size() == Global.MAX_BUFFER_SIZE){
					try {
						Global.queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				Global.queue.add(counter.incrementAndGet()) ;
				System.out.println("Producer#"+Thread.currentThread().getId()+": produced "+counter.get());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Global.queue.notifyAll();
			}
			
		}
		

	}
	
	public boolean terminate(){
		Thread.currentThread().interrupt();
		return true ;
	}
}
