/**
 * Copyright (c) 2016, Xianyi Ye
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
package com.yxy.chokonu.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ChangePrivateFinalStaticField {
	
	private static String VAR_STR = "1111" ;
	private static final int VAR_INT = 1111 ;
	
	public static void showVar(){
		System.out.println("VAR_STR="+VAR_STR);
		System.out.println("VAR_INT="+VAR_INT);
	}

	public static void setFinalStatic(Field field, Object newValue) throws Exception {
		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, Exception {
		System.out.println("Before:");
		showVar() ;
		
		setFinalStatic(ChangePrivateFinalStaticField.class.getDeclaredField("VAR_STR"), "2222");
		
		//This value is not changed, because the value of VAR_INT has been changed to constant during compling.
		//Therefore, setFinalStatic() only works for non-primitive data type instance.
		setFinalStatic(ChangePrivateFinalStaticField.class.getDeclaredField("VAR_INT"), 2222);
		
		System.out.println("After:");
		showVar() ;

	}

}
