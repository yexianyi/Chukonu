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
package com.yxy.chukonu.java.jdk18.inf.dflt.method;

public class DefaultMethod {
	
	interface A{
		void doSomething();
		
		default void hello() {
			System.out.println("A.hello()");
		}

		default void foo() {
			System.out.println("A.foo()");
		}

	}
	
	// B must be extended from A, otherwise compiling will be failed, because of 
	// duplicate methoed hello() and foo() are conflict.
	interface B extends A{ 
		default void hello() {
			System.out.println("B.hello()");
		}
		
		default void foo() {
			A.super.hello();
		    this.hello();
		    A.super.foo();
		}
	}
	
	static class C implements B, A {
	  
		@Override
	  public void doSomething() {
	    System.out.println("c.doSomething()");
	  }
		
	}
	  
	public static void main(String[] args) {
		A instance = new C();
		System.out.print("instance.hello() => "); //B.hello()
		instance.hello() ;
		
		System.out.print("instance.foo() => "); //A.hello(); B.hello(); A.foo()
		instance.foo();
		
		System.out.print("instance.doSomething() => "); //c.doSomething()
		instance.doSomething();
		
		System.out.print("new C().hello() => "); //B.hello()
		new C().hello();

	}

}
