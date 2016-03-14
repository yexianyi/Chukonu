package com.yxy.chokonu.java.dp;

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
