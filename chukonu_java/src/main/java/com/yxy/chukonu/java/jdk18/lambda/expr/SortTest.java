package com.yxy.chukonu.java.jdk18.lambda.expr;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortTest {
	
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
