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
