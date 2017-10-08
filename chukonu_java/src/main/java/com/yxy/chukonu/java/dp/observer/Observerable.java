package com.yxy.chukonu.java.dp.observer;

import java.util.Vector;

public class Observerable {

	private boolean isChanged = false ;
	
	private Vector<Observer> observerList = new Vector<Observer>() ;
	
	public void notifyObservers(Object arg) {
		 Observer[] observerArray;
		 synchronized(this){
			 if (!isChanged){
				 return;
			 }
			 observerArray = (Observer[]) observerList.toArray(new Observer[observerList.size()]) ;
			 clearChanged() ;
		 }//end syn
		 
		 for(int i=0; i<observerArray.length; i++){
			 observerArray[i].update(this, arg);
		 }
	}
	
	public synchronized void addObserver(Observer newObserver){
		observerList.add(newObserver) ;
	}
	
	public synchronized void removeObserver(Observer newObserver){
		observerList.remove(newObserver) ;
	}
	
	public synchronized void setChanged(){
		isChanged = true ;
	}
	
	public synchronized void clearChanged(){
		isChanged = false ;
	}
}
