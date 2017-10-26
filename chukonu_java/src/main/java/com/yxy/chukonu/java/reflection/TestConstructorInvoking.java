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
