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
package com.yxy.chukonu.java.multi.thread.rate.limit;

public class Consumer {

	public static void main(String[] args) {
		TokenBucket bucket = new TokenBucket(10, 1) ;
		Producer producer = new Producer(bucket) ;
		producer.start();
		
		int num = 5 ;
		
		for(int i=0; i<num; i++) {
			Thread consumer = new Thread() {
				@Override
				public void run() {
					while(true) {
						try {
							System.out.println("Thread:"+Thread.currentThread().getName()+" got token "+bucket.getToken());
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}
				
			};
			consumer.start();
		}//end for
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
