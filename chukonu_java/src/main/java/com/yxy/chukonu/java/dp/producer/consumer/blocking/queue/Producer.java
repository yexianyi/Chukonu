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
package com.yxy.chukonu.java.dp.producer.consumer.blocking.queue;

import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

	public static AtomicInteger counter = new AtomicInteger(0) ;
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			try {
				Global.queue.put(counter.incrementAndGet()) ;
				System.out.println("Producer#"+Thread.currentThread().getId()+": produced "+counter.get());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}
	
	public boolean terminate(){
		Thread.currentThread().interrupt();
		return true ;
	}

}
