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
package com.yxy.chukonu.java.jdk18.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

public class AnnotationExample {
	

	@interface Hints {
	    Hint[] value();
	}
	
	@Repeatable(Hints.class)
	@interface Hint {
	    String value();
	}

	//old way
	@Hints({@Hint("hint1"), @Hint("hint2")})
	class Person {}
	
	//new way
	@Hint("hint1")
	@Hint("hint2")
	class Person2 {}
	
	@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
	@interface MyAnnotation {}

	public static void main(String[] args) {

		Hint hint = Person.class.getAnnotation(Hint.class);
		System.out.println(hint);  // null
		
		Hints hints1 = Person.class.getAnnotation(Hints.class);
		System.out.println(hints1.value().length);  // 2
		
		Hint[] hints2 = Person.class.getAnnotationsByType(Hint.class);
		System.out.println(hints2.length);  // 2

		
	}

}
