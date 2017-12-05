package com.yxy.chukonu.java.jdk18.feature.inf.dflt.method;

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
