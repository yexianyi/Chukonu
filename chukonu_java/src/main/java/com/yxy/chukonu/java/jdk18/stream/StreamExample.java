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
package com.yxy.chukonu.java.jdk18.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamExample {
	public static List<String> list = Arrays.asList("ddd2","aaa2","bbb1","aaa1","bbb3","ccc","bbb2","ddd1");
	
	public static void filter(List<String> list){
		list.stream()
	    	.filter((s) -> s.startsWith("a"))
	    	.forEach(System.out::println);
	}
	
	
	public static void sort(List<String> list){
		list.stream()
		    .sorted()
		    .forEach(System.out::println);
	}
	
	
	public static void map(List<String> list){
		list.stream()
		    .map(String::toUpperCase)
		    .forEach(System.out::println);
	}
	
	
	public static boolean match(List<String> list){
		boolean anyStartsWithA = list.stream()
									 .anyMatch((s) -> s.startsWith("a")) ;
		return anyStartsWithA ;
	}
	
	
	public static long count(List<String> list){
		long num = list.stream()
					  .filter((s) -> s.startsWith("b"))
			          .count();
		return num ;
	}
	
	
	public static void reduced(List<String> list){
		Optional<String> reduced = list.stream()
								       .sorted()
								       .reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println);
	}
	
	public static void main(String[] args) {
		filter(list) ;
		sort(list) ;
		map(list) ;
		match(list) ;
		count(list) ;
		reduced(list) ;
	}
}
