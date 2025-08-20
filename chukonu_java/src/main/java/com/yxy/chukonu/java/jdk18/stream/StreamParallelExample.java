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
package com.yxy.chukonu.java.jdk18.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StreamParallelExample {
	
	private List dataCollection ;
	
	public StreamParallelExample(){
		int max = 1000000;
		dataCollection = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
		    UUID uuid = UUID.randomUUID();
		    dataCollection.add(uuid.toString());
		}
	}
	
	public long serialSort(){
		long t0 = System.nanoTime();
		long count = dataCollection.stream().sorted().count();
		long t1 = System.nanoTime();

		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		return millis ;
	}
	
	
	public long parallelSort(){
		long t0 = System.nanoTime();
		long count = dataCollection.parallelStream().sorted().count();
		long t1 = System.nanoTime();
		
		long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
		return millis ;
	}
	

	public static void main(String[] args) {
		StreamParallelExample example = new StreamParallelExample() ;
//		System.out.println(example.serialSort()) ;
		System.out.println(example.parallelSort()) ;
	}

}
