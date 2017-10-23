package com.yxy.chukonu.java.polymorphic.extend;

public class Base {

	private String private_str = "base_private_str" ;
	
	protected String protected_str = "base_protected_str" ;
	
	public static String static_pub_val = "base_static_pub_val" ;
	
	
	public void non_static_test(){
		System.out.println("base_non_static_test()");
	}
	
	public static void static_test(){
		System.out.println("base_static_test()");
	}
	
}
