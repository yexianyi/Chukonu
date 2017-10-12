package com.yxy.chukonu.java.dp.observer;

public class Test {

	public static void main(String[] args) {
		ObserverableOrder order = new ObserverableOrder(1.10f) ;
		order.addObserver(new ObserverA("Email"));
		order.addObserver(new ObserverA("Phone"));
		
		order.modifyPrice(2.20f);
	}

}
