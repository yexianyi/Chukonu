package com.yxy.chukonu.java.dp.producer.consumer.raw;


public class Consumer extends Thread {
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			synchronized(Global.queue){
				while(Global.queue.size() == 0){
					try {
						Global.queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("Consumer#"+Thread.currentThread().getId()+": consumed "+ Global.queue.get(0));
				Global.queue.remove(0) ;
				try {
					Thread.sleep(500);
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
