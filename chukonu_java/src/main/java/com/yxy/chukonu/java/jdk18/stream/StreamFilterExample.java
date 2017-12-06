package com.yxy.chukonu.java.jdk18.stream;

import java.util.List;

public class StreamFilterExample extends StreamExample {

	public static void filter(List<String> list){
		list.stream()
	    	.filter((s) -> s.startsWith("a"))
	    	.forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		filter(list) ;
	}

}
