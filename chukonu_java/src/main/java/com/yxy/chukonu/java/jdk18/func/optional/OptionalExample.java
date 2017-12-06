package com.yxy.chukonu.java.jdk18.func.optional;

import java.util.Optional;

public class OptionalExample {

	public static void main(String[] args) {
//		String str = "bam" ;
//		Optional<String> optional = Optional.of(str);
		String val = null ;
		Optional<String> optional = Optional.ofNullable(val) ;

		System.out.println(optional.isPresent());           // true
		if(optional.isPresent()){
			System.out.println(optional.get());        
			System.out.println(optional.orElse("fallback"));    // "bam"
		}
		optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"

	}

}
