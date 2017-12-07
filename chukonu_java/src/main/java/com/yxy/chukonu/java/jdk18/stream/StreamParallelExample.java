package com.yxy.chukonu.java.jdk18.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StreamParallelExample {
	
	private List dataCollection ;
	
	public StreamParallelExample(){
		int max = 1000000;
		dataCollection = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
		    UUID uuid = UUID.randomUUID();
		    dataCollection.add(uuid.toString());
		}
	}
	
	public long serialSort(){
		long t0 = System.nanoTime();
		long count = dataCollection.stream().sorted().count();
		long t1 = System.nanoTime();

		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		return millis ;
	}
	
	
	public long parallelSort(){
		long t0 = System.nanoTime();
		long count = dataCollection.parallelStream().sorted().count();
		long t1 = System.nanoTime();
		
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		return millis ;
	}
	

	public static void main(String[] args) {
		StreamParallelExample example = new StreamParallelExample() ;
//		System.out.println(example.serialSort()) ;
		System.out.println(example.parallelSort()) ;
	}

}
