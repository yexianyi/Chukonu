package com.yxy.chukonu.java.dp.factory;

public class AbastractFactory {

	public static interface Product{
	}
	
	public static class ProductA implements Product{
	}
	
	public static class ProductA1 extends ProductA{
	}
	
	public static class ProductA2 extends ProductA{
	}
	
	public static class ProductB implements Product{
	}
	
	public static class ProductB1 extends ProductB{
	}
	
	public static class ProductB2 extends ProductB{
	}
	
	public static class ProductC implements Product{
	}
	
	public static class ProductC1 extends ProductC{
	}
	
	public static class ProductC2 extends ProductC{
	}
	
	public static interface Factory{
		public ProductA factoryA() ;
		
		public ProductB factoryB() ;
		
		public ProductC factoryC() ;
	}
	
	
	public static class Factory1 implements Factory{

		@Override
		public ProductA factoryA() {
			return new ProductA1();
		}

		@Override
		public ProductB factoryB() {
			return new ProductB1();
		}

		@Override
		public ProductC factoryC() {
			return new ProductC1();
		}
		
	}
	
	
	
	public static class Factory2 implements Factory{

		@Override
		public ProductA factoryA() {
			return new ProductA2();
		}

		@Override
		public ProductB factoryB() {
			return new ProductB2();
		}

		@Override
		public ProductC factoryC() {
			return new ProductC2();
		}
		
	}
	
}
