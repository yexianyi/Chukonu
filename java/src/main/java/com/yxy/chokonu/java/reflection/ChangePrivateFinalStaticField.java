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
