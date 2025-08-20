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
package com.yxy.chukonu.java.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * This test class is for validating if constructor will be called during reflection.
 * 
 * @author xianyiye
 *
 */
public class TestConstructorInvoking {
	
	public TestConstructorInvoking(){
		System.out.println("General constructor");
	}
	
	public TestConstructorInvoking(String param){
		System.out.println("Constructor with param");
	}
	
	public void testMethod(){
		System.out.println("testMethod");
	}

	public static void main(String[] args) {
		try {
			TestConstructorInvoking object  = (TestConstructorInvoking) Class.forName("com.yxy.chukonu.java.reflection.TestConstructorInvoking").newInstance() ;
			object.testMethod();
			
			TestConstructorInvoking object2  = (TestConstructorInvoking) Class.forName("com.yxy.chukonu.java.reflection.TestConstructorInvoking").getConstructor(String.class).newInstance("test") ;
			object2.testMethod();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		

	}

}
