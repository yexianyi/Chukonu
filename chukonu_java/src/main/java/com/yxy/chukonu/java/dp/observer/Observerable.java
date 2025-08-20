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
