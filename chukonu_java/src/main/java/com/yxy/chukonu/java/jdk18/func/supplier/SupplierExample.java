package com.yxy.chukonu.java.jdk18.func.supplier;

import java.util.function.Supplier;

import com.yxy.chukonu.java.jdk18.method.and.constructor.invoke.ConstructorInvoking;

public class SupplierExample {
	
	static class Person {
	    String firstName;
	    String lastName;
	    int age;

	    Person() {}
	}

	public static void main(String[] args) {
		Supplier<Person> personSupplier = Person::new;
		Person person = personSupplier.get();   // new Person
		System.out.println(person.firstName) ;
	}

}
