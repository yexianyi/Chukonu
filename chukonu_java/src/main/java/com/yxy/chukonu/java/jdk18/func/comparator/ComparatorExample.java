package com.yxy.chukonu.java.jdk18.func.comparator;

import java.util.Comparator;

public class ComparatorExample {
	
	static class Person {
	    String firstName;
	    String lastName;
	    int age;

	    Person(String firstName, String lastName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }
	}

	public static void main(String[] args) {
		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);

		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Alice", "Wonderland");

		comparator.compare(p1, p2);             // > 0
		comparator.reversed().compare(p1, p2);  // < 0

	}

}
