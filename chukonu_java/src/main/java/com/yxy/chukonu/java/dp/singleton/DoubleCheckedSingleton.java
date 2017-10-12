package com.yxy.chukonu.java.dp.singleton;

public class DoubleCheckedSingleton {
	private static volatile DoubleCheckedSingleton instance ; //volatile is a must
	
	private DoubleCheckedSingleton(){
		
	}
	
	public static DoubleCheckedSingleton getInstance(){
		if(instance==null){
			synchronized(DoubleCheckedSingleton.class){
				if(instance==null){
					instance = new DoubleCheckedSingleton() ;
				}
			}
		}
		
		return instance ;
	}
	
	
}
