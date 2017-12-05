package com.yxy.chukonu.java.jdk18.func.function;

import java.util.function.Function;

public class FunctionExample {

	public static void main(String[] args) {

		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		backToString.apply("123");     // "123"

	}

}
