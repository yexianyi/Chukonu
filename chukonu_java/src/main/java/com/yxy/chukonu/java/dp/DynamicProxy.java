/**
 * Copyright (c) 2016, Xianyi Ye
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
package com.yxy.chukonu.java.dp;

import java.lang.reflect.Method;

/**
 * Java Dynamic Proxy:How to know what exception cause
 * java.lang.reflect.InvocationTargetException 
 * 
 * Issue:
 * 
 * When we use Java Dynamic Proxy, method.invoke() is a necessary step to call
 * the real method we want. But once this method triggers an exception during
 * execution, the final exception printed out is always
 * "java.lang.reflect.InvocationTargetException". So, how can we know that which
 * exact exception caused JVM throw InvocationTargetException?
 * 
 * Solution:
 * We can use e.getCause() to get this info, such as
 * 
 */

public class DynamicProxy {

	public Object invoke(Object proxy, Method method, Object[] obj) throws Throwable {

		Object result = null;

		try {
			result = method.invoke("delegation", obj);
		} catch (Exception e) {
			matchException(e);
		}

		return result;
	}

	private void matchException(Exception e) {
		System.out.println(e.getCause());

		if (e.getCause() instanceof SubException) {
			System.out.println("This is SubException.");

		}

		else if (e.getCause() instanceof BaseException) {
			System.out.println("This is BaseException.");

		}
	}

}
