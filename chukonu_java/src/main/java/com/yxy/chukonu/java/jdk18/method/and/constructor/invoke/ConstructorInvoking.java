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

public class ConstructorInvoking {
	static class Person {
	    String firstName;
	    String lastName;
	    int age;

	    Person() {}
	    
	    Person(String firstName) {
	        this.firstName = firstName;
	    }

	    Person(String firstName, String lastName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }
	    
	    Person(String firstName, String lastName, String age) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }
	}
	
	interface PersonFactory<P extends Person> {
	    P create(String firstName, String lastName);
	}

	public static void main(String args[]){
		PersonFactory<Person> personFactoryImpl = Person::new;
		//no need to implement a factory method. Instead, Java Compiler will help to match proper constructor automatically.
		Person person = personFactoryImpl.create("Peter", "Parker");
		System.out.println(person.firstName + " " + person.lastName);
	}
}
