package com.yxy.chukonu.java.dp.producer.consumer.raw;


public class Test {

	public static void main(String[] args) {
		Producer producer1 = new Producer() ;
		Producer producer2 = new Producer() ;
		Producer producer3 = new Producer() ;
		
		Consumer consumer1 = new Consumer() ;
		Consumer consumer2 = new Consumer() ;
		
		producer1.start();
//		producer2.start();
//		producer3.start();
		
		consumer1.start();
		consumer2.start();

	}

}
