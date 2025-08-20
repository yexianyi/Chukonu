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
package com.yxy.chukonu.java.jdk18.lambda.expr;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortTest {
	
	/**
	 * 	(args) -> expr
		(args) -> statement
		(args) -> { statement句 } 
	 */
	
	public static void main(String[] args) {
		
		//Old method
		List<String> list = Arrays.asList("A", "C", "E", "D");
		Collections.sort(list, new Comparator<String>() {
		    @Override
		    public int compare(String a, String b) {
		        return b.compareTo(a);
		    }
		});
		
		for(String item : list){
			System.out.print(item+" ") ;
		}
		
		System.out.println() ;
		
		//JDK1.8 new feature - Lambda (1)
		List<String> list2 = Arrays.asList("A", "C", "E", "D");
		Collections.sort(list2, (String a, String b) -> {
		    return b.compareTo(a);
		});
		
		for(String item : list2){
			System.out.print(item+" ") ;
		}
		
		System.out.println() ;
		
		//JDK1.8 new feature - Lambda (2)
		List<String> list3 = Arrays.asList("A", "C", "E", "D");
		Collections.sort(list3, (String a, String b) -> b.compareTo(a));
		
		for(String item : list3){
			System.out.print(item+" ") ;
		}
		
		System.out.println() ;
		
		//JDK1.8 new feature - Lambda (3)
		List<String> list4 = Arrays.asList("A", "C", "E", "D");
		//If there is only one line in method body, "{}" and "return" keywords could be omitted.
		//args data types could be inferred automatically.
		Collections.sort(list4, (a, b) -> b.compareTo(a));
		
		for(String item : list4){
			System.out.print(item+" ") ;
		}
				
	}

}
