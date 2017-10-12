package com.yxy.chukonu.java.dp.observer;

public class ObserverA implements Observer {

	private String name ;
	
	public ObserverA(String name){
		this.name = name ;
	}
	
	@Override
	public void update(Observerable object, Object args) {
		float newPrice = (float) args ;
		System.out.println(name + " has received price changing notification with new price is "+newPrice);
		
	}

}
