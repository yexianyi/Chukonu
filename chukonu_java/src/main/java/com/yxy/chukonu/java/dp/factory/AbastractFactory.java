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
