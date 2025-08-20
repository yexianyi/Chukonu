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
package com.yxy.chukonu.java.jdk18.map;

import java.util.HashMap;
import java.util.Map;

public class MapExample {

	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
		    map.putIfAbsent(i, "val" + i);
		}
		
		map.forEach((id, val) -> System.out.println(id + " = " +val));
		
		map.computeIfPresent(3, (num, val) -> val + num);
		map.get(3);             // val33
		
		map.computeIfPresent(9, (num, val) -> null);
		map.containsKey(9);     // false
		
		map.computeIfAbsent(23, num -> "val" + num);
		map.containsKey(23);    // true
		
		map.computeIfAbsent(3, num -> "bam");
		map.get(3);             // val33

		map.remove(3, "val3");
		map.get(3);             // val33

		map.remove(3, "val33");
		map.get(3);             // null
		
		map.getOrDefault(42, "not found");  // not found
		
		map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
		map.get(9);             // val9

		map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
		map.get(9);             // val9concat


	}

}
