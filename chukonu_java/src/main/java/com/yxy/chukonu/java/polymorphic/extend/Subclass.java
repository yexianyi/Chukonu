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
package com.yxy.chukonu.java.polymorphic.extend;

public class Subclass extends Base {
	private String private_str = "subclass_private_str" ;
	
	protected String protected_str = "subclass_protected_str" ;
	
	public static String static_pub_val = "subclass_static_pub_val" ;
	
	@Override
	public void non_static_test(){
		System.out.println("Subclass_non_static_test()");
	}
	
	public static void static_test(){
		System.out.println("Subclass_static_test()");
	}
}
