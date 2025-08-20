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
