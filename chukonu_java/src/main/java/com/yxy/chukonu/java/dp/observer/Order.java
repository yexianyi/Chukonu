package com.yxy.chukonu.java.dp.observer;

public class Order extends Observerable{

	private float price ;
	
	public Order(float price){
		this.price = price ;
	}
	
	public void modifyPrice(float newPrice){
		price = newPrice ;
		
		setChanged() ;
		notifyObservers(newPrice);
	}
	
}
