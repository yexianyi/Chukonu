package com.yxy.chukonu.java.dp.factory;

public class SimpleFactory {
	
	public static Product factory(String proName){
		if(proName == "A")
			return new ProductA() ;
		else if(proName == "B")
			return new ProductB() ;
		else if(proName == "C")
			return new ProductC() ;
		else
			return null ;
	}

	
	public static interface Product{
		
	}
	
	public static class ProductA implements Product{
		
	}
	
	public static class ProductB implements Product{
			
		}
	
	public static class ProductC implements Product{
		
	}
}
