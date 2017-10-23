package com.yxy.chukonu.java.polymorphic.extend;

public class Subclass extends Base {
	private String private_str = "subclass_private_str" ;
	
	protected String protected_str = "subclass_protected_str" ;
	
	public static String static_pub_val = "subclass_static_pub_val" ;
	
	@Override
	public void non_static_test(){
		System.out.println("Subclass_non_static_test()");
	}
	
	public static void static_test(){
		System.out.println("Subclass_static_test()");
	}
}
