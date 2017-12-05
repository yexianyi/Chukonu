package com.yxy.chukonu.java.jdk18.func.consumer;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ConsumerExample {
	
	static class Customer {
		String name ;
	    int level; 
	    Double discount = 0.0; 
	    Double originalPrice = 100.0;

	    Customer(String name) {
	    	this.name = name ;
	    }
	    
	    public Double getFee(){
	    	return originalPrice*discount ;
	    }
	    
	}
	
	static class Service{

		public static Customer updateFee(Customer customer, Predicate<Customer> predicate, Consumer<Customer> consumer) {
			// Use the predicate to decide when to update the discount.
			if (predicate.test(customer)) {
				// Use the consumer to update the discount value.
				consumer.accept(customer);
			}
			return customer;
		}
	}
	

	public static void main(String[] args) {
		Customer customer1 = new Customer("Jack") ;
		customer1.level = 6 ;
		customer1 = Service.updateFee(customer1, person->person.level > 5,  person->person.discount = 0.9); 
		System.out.println(customer1.getFee()) ;
		
		customer1 = Service.updateFee(customer1, person->person.level > 8,  person->person.discount = 0.8);
		System.out.println(customer1.getFee()) ;
	}

}
