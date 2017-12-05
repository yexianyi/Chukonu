package com.yxy.chukonu.java.jdk18.feature.method.and.constructor.invoke;

import com.yxy.chukonu.java.jdk18.feature.func.inf.FuncInterface;

public class StaticMethodRef {

	public static void main(String[] args) {
		//(from) -> Integer.valueOf(from);  => Integer::valueOf;
		FuncInterface<String, Integer> funcImpl = Integer::valueOf;
		Integer converted = funcImpl.convert("123");
		System.out.println(converted);   // 123
	}

}
