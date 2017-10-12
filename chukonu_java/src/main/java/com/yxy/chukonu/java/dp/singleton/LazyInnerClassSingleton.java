package com.yxy.chukonu.java.dp.singleton;

public class LazyInnerClassSingleton {

	private LazyInnerClassSingleton(){
		
	}
	
	private static class InnerClass{
		public static final LazyInnerClassSingleton instance = new LazyInnerClassSingleton();
		
	}
	
	public static LazyInnerClassSingleton getInstance(){
		return InnerClass.instance ;
	}
	
}
