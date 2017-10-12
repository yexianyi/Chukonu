package com.yxy.chukonu.java.dp.factory;

public class FactoryMethod {
	
	public static interface Product{
	}
	
	public static class ProductA implements Product{
	}
	
	public static class ProductB implements Product{
	}
	
	public static interface Factory{
		public Product factory() ;
	}
	
	public static class FactoryA implements Factory{
		@Override
		public Product factory() {
			return new ProductA();
		}
	}
	
	public static class FactoryB implements Factory{
		@Override
		public Product factory() {
			return new ProductB();
		}
	}
}
