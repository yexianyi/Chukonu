package com.yxy.chukonu.java.dp.observer;

public class ObserverableOrder extends Observerable{

	private float price ;
	
	public ObserverableOrder(float price){
		this.price = price ;
	}
	
	public void modifyPrice(float newPrice){
		price = newPrice ;
		
		setChanged() ;
		notifyObservers(newPrice);
	}
	
}
