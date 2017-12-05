package com.yxy.chukonu.java.jdk18.lambda.scope;

import com.yxy.chukonu.java.jdk18.func.inf.FuncInterface;

public class LocalVariableExample {
	
	public static String accessVariable(int var){
		//final keyword is not mandatory here,
		//however, num cannot be changed, otherwise compiling will be failed.
		//In other words, num has been added "final" keyword implicitly.
		final int num = 1; 
		FuncInterface<Integer, String> funcImpl = (from) -> String.valueOf(from + num);
		return funcImpl.convert(var);     // 3
	}

	public static void main(String[] args) {
		System.out.println(accessVariable(3)) ;
	}

}
