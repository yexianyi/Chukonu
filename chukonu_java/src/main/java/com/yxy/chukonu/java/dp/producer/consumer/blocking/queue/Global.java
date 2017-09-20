package com.yxy.chukonu.java.dp.producer.consumer.blocking.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class Global {

	public static final BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(10); 
	
}
