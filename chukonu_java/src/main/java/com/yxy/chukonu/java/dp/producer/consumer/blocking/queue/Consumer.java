package com.yxy.chukonu.java.dp.producer.consumer.blocking.queue;

public class Consumer extends Thread {

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			try {
				System.out.println("Consumer#"+Thread.currentThread().getId()+": consumed "+Global.queue.take());
				Thread.sleep(500);
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
