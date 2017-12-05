package com.yxy.chukonu.java.jdk18.feature.method.and.constructor.invoke;

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
