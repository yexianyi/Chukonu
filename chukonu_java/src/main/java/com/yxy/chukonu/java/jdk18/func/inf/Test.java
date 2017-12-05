package com.yxy.chukonu.java.jdk18.func.inf;

public class Test {

	public static void main(String[] args) {
		FuncInterface<String, Integer> converter = (from) -> Integer.valueOf(from);
		Integer converted = converter.convert("123");
		System.out.println(converted);    // 123
	}

}
