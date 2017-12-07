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
