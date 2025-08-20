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
