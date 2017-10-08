package com.yxy.chukonu.java.dp.observer;

public class Test {

	public static void main(String[] args) {
		Order order = new Order(1.10f) ;
		order.addObserver(new Messager("Email"));
		order.addObserver(new Messager("Phone"));
		
		order.modifyPrice(2.20f);
	}

}
