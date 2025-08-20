/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.java.jdk18.method.and.constructor.invoke;

import com.yxy.chukonu.java.jdk18.func.inf.FuncInterface;

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
