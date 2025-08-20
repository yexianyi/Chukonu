/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
