package com.yxy.chukonu.java.jdk18.feature.method.and.constructor.invoke;

import com.yxy.chukonu.java.jdk18.feature.func.inf.FuncInterface;

public class MethodInvoking {
	
	//refer to static method 
	public static void referStaticMethod(String numberStr){
		//(from) -> Integer.valueOf(from);  => Integer::valueOf;
		FuncInterface<String, Integer> funcImpl = Integer::valueOf;
		Integer converted = funcImpl.convert(numberStr);
		System.out.println(converted);   // 123
	}
	
	//refer to object method 
	public static void referObjectMethod(String str, String endsWith){
		FuncInterface<String, Boolean> funcImpl = str::endsWith;
		Boolean converted = funcImpl.convert(endsWith);
		System.out.println(converted);   
	}

	public static void main(String[] args) {
		referStaticMethod("123") ;
		referObjectMethod("abc", "c") ;
	}

}
